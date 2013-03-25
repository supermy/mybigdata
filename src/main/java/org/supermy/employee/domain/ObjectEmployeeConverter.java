package org.supermy.employee.domain;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ObjectEmployeeConverter  implements Converter<Object, DBObject> {

	
	public DBObject convert(Object o) {
		Employee empl = (Employee)o;
		DBObject doc = new BasicDBObject();
		
		//doc.put("_id", empl.getId());//FIXME
		
		doc.put("_id", new Date().getTime()+empl.getId());
		doc.put("name", empl.getName());
		doc.put("salary", empl.getSalary());
		
		System.out.println("convert:"+doc);
		return doc;
	}
	
}
