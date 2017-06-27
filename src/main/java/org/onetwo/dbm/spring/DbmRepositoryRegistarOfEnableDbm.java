package org.onetwo.dbm.spring;

import org.onetwo.common.db.dquery.RichModelAndQueryObjectScanTrigger;
import org.onetwo.common.spring.SpringUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class DbmRepositoryRegistarOfEnableDbm implements ImportBeanDefinitionRegistrar {
	
	/***
	 * @see org.onetwo.common.db.dquery.DynamicQueryHandlerProxyCreator#defaultQueryProvideManagerClass
	 */
	public static final String ATTR_DEFAULT_QUERY_PROVIDE_MANAGER_CLASS = "defaultQueryProvideManagerClass";

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes attributes = SpringUtils.getAnnotationAttributes(importingClassMetadata, EnableDbm.class);
		if (attributes == null) {
			throw new IllegalArgumentException(String.format("@%s is not present on importing class '%s' as expected", EnableDbm.class.getSimpleName(), importingClassMetadata.getClassName()));
		}
		
		RichModelAndQueryObjectScanTrigger register = new RichModelAndQueryObjectScanTrigger(registry);
		register.setPackagesToScan(attributes.getStringArray("packagesToScan"));
		register.setEnableRichModel(attributes.getBoolean("enableRichModel"));
		register.scanAndRegisterBeans((ListableBeanFactory)registry);
	}

}
