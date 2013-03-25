package org.supermy.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@SuppressWarnings("restriction")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	private static String dialect = ""; // 数据库方言
	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	public Object intercept(Invocation ivk) throws Throwable {
		// TODO Auto-generated method stub
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");

			if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				} else {

					Pages page = null;
					if (parameterObject instanceof Pages) { // 参数就是Pages实体
						page = (Pages) parameterObject;

					} else if (parameterObject instanceof Map) {
						Map<String, Object> a = (Map<String, Object>) parameterObject;
						Object b = a.get("page");

						if (b != null) {
							page = (Pages) b;
//							if (page == null) {
//								return ivk.proceed();
//							}
						} else {
							// 不存在page属性
							return ivk.proceed();
						}
					} else { // 参数为某个实体，该实体拥有Pages属性

						Field pageField = ReflectHelper.getFieldByFieldName(
								parameterObject, "page");

						System.out
								.println(">>>>>>>>>>>>>>>>>>拦截需要分页的SQL:pageField "
										+ pageField);

						if (pageField != null) {
							page = (Pages) ReflectHelper.getValueByFieldName(
									parameterObject, "page");
							System.out
									.println(">>>>>>>>>>>>>>>>>>拦截需要分页的SQL:page"
											+ page);
							if (page == null) {
								return ivk.proceed();
							}
						} else {
							// 不存在page属性
							return ivk.proceed();
						}
					}
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					
					String countSql = "select count(0) from (" + sql
							+ ") tmp_count"; // 记录统计
					
					if ("mysql".equals(dialect)) {
						System.out.println(">>>>>>>>>>>>"+sql);
						String sql1=sql.substring(sql.indexOf(" from"));
						countSql = "select count(*) " + sql1; // 记录统计
					} else if ("hsql".equals(dialect)) {
						System.out.println(">>>>>>>>>>>>"+sql);
						String sql1=sql.substring(sql.indexOf(" from"));
						countSql = "select count(*) " + sql1; // 记录统计
					} else if ("oracle".equals(dialect)) {
						countSql = "select count(0) from (" + sql
								+ ") tmp_count"; // 记录统计
					}
					
					
					PreparedStatement countStmt = connection
							.prepareStatement(countSql);
					ReflectHelper
							.setValueByFieldName(boundSql, "sql", countSql);
					DefaultParameterHandler ParameterHandler = new DefaultParameterHandler(
							mappedStatement, parameterObject, boundSql);
					ParameterHandler.setParameters(countStmt);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					page.setTotalCount(count);
					String pageSql = generatePagesSql(sql, page);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.

				}
			}
		}
		return ivk.proceed();
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePagesSql(String sql, Pages page) {
		if (page != null && StringUtils.isNotEmpty(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			if ("mysql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + page.getCurrentPage() + ","
						+ page.getPageSize());
			} else if ("hsql".equals(dialect)) {
				String limit=" limit " + page.getCurrentPage() + " "
						+ page.getPageSize()+" ";
				sql=sql.replaceAll("select ", "select "+limit);
				pageSql.append(sql);
				
			}else if ("oracle".equals(dialect)) {
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrentPage() * page.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append((page.getCurrentPage() - 1) * page.getPageSize());
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
