<#assign requestPath="${_globalConfig.requestModulePath}/${_tableContext.propertyName}"/>
<#assign pagePath="${_globalConfig.requestModulePath}/${_tableContext.tableNameWithoutPrefix?replace('_', '-')}"/>

<#assign servicePackage="${_globalConfig.javaModulePackage}.service"/>
<#assign serviceImplPackage="${_globalConfig.javaModulePackage}.service.impl"/>
<#assign daoPackage="${_globalConfig.javaModulePackage}.dao"/>
<#assign entityPackage="${_globalConfig.javaModulePackage}.entity"/>

<#assign entityClassName="${_tableContext.className}Entity"/>
<#assign entityClassName2="${_tableContext.className}"/>
<#assign serviceImplClassName="${_tableContext.className}ServiceImpl"/>
<#assign serviceImplPropertyName="${_tableContext.propertyName}Service"/>
<#assign mapperClassName="${_tableContext.className}Mapper"/>
<#assign mapperPropertyName="${_tableContext.propertyName}Mapper"/>
<#assign idName="${table.primaryKey.javaName}"/>
<#assign idType="${table.primaryKey.javaType.simpleName}"/>


package ${_globalConfig.getJavaLocalPackage(_tableContext.localPackage)};

import org.onetwo.boot.core.web.controller.DateInitBinder;
import org.onetwo.common.utils.Page;
import org.onetwo.common.utils.PageRequest;
import org.onetwo.ext.permission.api.annotation.ByPermissionClass;
import org.onetwo.ext.permission.api.PermissionType;
import org.onetwo.ext.permission.api.annotation.PermissionMeta;

import org.onetwo.common.data.Result;
import org.onetwo.common.spring.mvc.utils.DataResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.onetwo.common.spring.validator.ValidatorUtils;
import org.onetwo.common.spring.validator.ValidatorUtils.ValidGroup.ValidAnyTime;
import org.onetwo.common.spring.validator.ValidatorUtils.ValidGroup.ValidWhenEdit;
import org.onetwo.common.spring.validator.ValidatorUtils.ValidGroup.ValidWhenNew;

import ${entityPackage}.${entityClassName};
import ${serviceImplPackage}.${serviceImplClassName};

@RestController
@RequestMapping("${requestPath}")
public class ${_tableContext.className}MgrController extends ${pluginBaseController} implements DateInitBinder {

    @Autowired
    private ${serviceImplClassName} ${serviceImplPropertyName};
    
    
    @ByPermissionClass(${_tableContext.className}Mgr.class)//在菜单类新建 ${_tableContext.className}Mgr 类后用import
    @GetMapping
    public Page<${entityClassName}> list(PageRequest pageRequest, ${entityClassName} ${_tableContext.propertyName}){
        Page<${entityClassName}> page = ${serviceImplPropertyName}.findPage(pageRequest.toPageObject(), ${_tableContext.propertyName});
        return page;
    }
    
    @ByPermissionClass(${_tableContext.className}Mgr.Create.class)
    @PostMapping
    public ${entityClassName} create(@Validated({ValidAnyTime.class, ValidWhenNew.class}) ${entityClassName} ${_tableContext.propertyName}, BindingResult br){
        ValidatorUtils.throwIfHasErrors(br, true);
        ${serviceImplPropertyName}.save(${_tableContext.propertyName});
        return ${_tableContext.propertyName};
    }

    @ByPermissionClass(${_tableContext.className}Mgr.class)
    @GetMapping(value="{${idName}}")
    public ${entityClassName} get(@PathVariable("${idName}") ${idType} ${idName}){
        ${entityClassName} ${_tableContext.propertyName} = ${serviceImplPropertyName}.findById(${idName});
        return ${_tableContext.propertyName};
    }
    
    @ByPermissionClass(${_tableContext.className}Mgr.Update.class)
    @PutMapping(value="{${idName}}")
    public ${entityClassName} update(@PathVariable("${idName}") ${idType} ${idName}, @Validated({ValidAnyTime.class, ValidWhenEdit.class}) ${entityClassName} ${_tableContext.propertyName}, BindingResult br){
        ValidatorUtils.throwIfHasErrors(br, true);
        ${_tableContext.propertyName}.set${idName?cap_first}(${idName});
        ${serviceImplPropertyName}.update(${_tableContext.propertyName});
        return ${_tableContext.propertyName};
    }
    
    
    @ByPermissionClass(${_tableContext.className}Mgr.Delete.class)
    @DeleteMapping
    public Result deleteBatch(${idType}[] ${idName}s){
        ${serviceImplPropertyName}.removeByIds(${idName}s);
        return DataResults.success("删除成功！").build();
    }
    
    /****
     * ${(table.comments[0])!''} 权限类
     */
    public static interface ${_tableContext.className}Mgr {
        String name = "${(table.comments[0])!''}管理";
        
        @PermissionMeta(name = "新增", permissionType=PermissionType.FUNCTION)
        public interface Create {
        }

        @PermissionMeta(name = "更新", permissionType=PermissionType.FUNCTION)
        public interface Update {
        }

        @PermissionMeta(name = "删除", permissionType=PermissionType.FUNCTION)
        public interface Delete {
        }
    }
}
