package ${modelPackage}.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.masonry.service.${entityName}Service;
import com.c306.core.base.action.BaseGenericActionSupport;
import ${modelPackage}.entity.${entityName};


public class ${entityName}Action extends BaseGenericActionSupport<${entityName}> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ${entityName}Service service;
	
	@Override
	protected ${entityName}Service getService() {
		return service;
	}
	

	/*
	@Override
	public String edit() throws Exception {
		return super.edit();
	}
	*/
	
	/*
	@Override
	public String saveOrUpdate() throws Exception {
		return super.saveOrUpdate();
	}
	*/
	
	/*
	@Override
	public String list() throws Exception {
//		Collection<? extends GrantedAuthority> autths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		logger.info(autths.toString());
//		//admin用户可查询全部
//		if(!(autths.contains(new GrantedAuthorityImpl("ROLE_ADMIN"))
//				|| autths.contains(new GrantedAuthorityImpl("ROLE_DESIGNER"))
//				|| autths.contains(new GrantedAuthorityImpl("ROLE_SEO"))
//				 )){
//			this.query.setAuthor(((SecUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
//		}

		return super.list();
	}
	*/
	
	
	
}
