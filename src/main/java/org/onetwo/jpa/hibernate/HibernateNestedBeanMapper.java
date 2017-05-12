package org.onetwo.jpa.hibernate;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;
import org.onetwo.dbm.annotation.DbmResultMapping;
import org.onetwo.dbm.jdbc.mapper.AbstractNestedBeanMapper;
import org.onetwo.dbm.jdbc.spi.ColumnValueGetter;
import org.onetwo.dbm.mapping.DbmMappedField;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class HibernateNestedBeanMapper<T> extends AbstractNestedBeanMapper<T> implements ResultTransformer {

	public HibernateNestedBeanMapper(Class<T> mappedClass, DbmResultMapping dbmResultMapping) {
		super(mappedClass, dbmResultMapping);
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Assert.state(this.mappedClass != null, "Mapped class was not specified");
		
		Map<String, Integer> names = Maps.newHashMapWithExpectedSize(aliases.length);
		for (int i = 0; i < aliases.length; i++) {
			names.put(aliases[i], i);
		}
		ColumnValueGetter columnValueGetter = new HibernateColumnValueGetter(tuple);
		Object mappedObject = this.resultClassMapper.mapResult(names, columnValueGetter);
		return mappedObject;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}
	
	public static class HibernateColumnValueGetter implements ColumnValueGetter {
		private Object[] tuple;
		
		public HibernateColumnValueGetter(Object[] tuple) {
			super();
			this.tuple = tuple;
		}

		@Override
		public Object getColumnValue(int index, PropertyDescriptor pd) {
			return tuple[index];
		}

		@Override
		public Object getColumnValue(int index, DbmMappedField field) {
			return tuple[index];
		}
		
	}

}
