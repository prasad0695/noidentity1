package com.jsfspring.curddemo.mbean;

import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.repositry.CompanyRepo;


@Controller("companyMBean")
@SessionScope
public class CompanyMBean{
	
	
	@Autowired
	public CompanyRepo companyRepo;
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	

	private Company company; 
	String addCompany = "/jsfspring/pages/company/addCompany.xhtml";
	String companyOverview = "/jsfspring/pages/company/company.xhtml";

	public CompanyMBean() {
		company = new Company();
	}

	public void newCompany() {
		reset();
		sukiBaseBean.pageRedirect(addCompany);
	}

	public void reset() {
		company = new Company();
	}

	
	public void saveCompany() {
		try {
			boolean flag=false;
			if(sukiBaseBean.validateString(company.getCompName(), "Company Name"))
				flag=true;
			if(flag)
				return;
			if (company.getCompId() > 0) {
//				company = CommonAPI.getInstance().update(company);
				sukiBaseBean.addMessage("Company", "Updated Successfullly");
			} else {
//				company = CommonAPI.getInstance().save(company);
				sukiBaseBean.addMessage("Company", "Saved Successfullly");
			}
			company=companyRepo.save(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCompanyObjActionEvent(ActionEvent event) {
		company = new Company();
		company = companyRepo.findById(sukiBaseBean.actionEvent(event)).get();
		sukiBaseBean.pageRedirect(addCompany);
	}

	public void getDeleteActionEvent(ActionEvent event) {
			companyRepo.deleteById(sukiBaseBean.actionEvent(event));
			sukiBaseBean.addMessage("Company", "Deleted Successfullly");
	}

	public void delete() {
//			company = (Company) sukiBaseBean.t;
			companyRepo.delete(company);
			sukiBaseBean.addMessage("Company", "Deleted Successfullly");
			sukiBaseBean.dialogHide();
			company = new Company();
	}

	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(companyOverview);
		sukiBaseBean.t = company;
		sukiBaseBean.overviewList();
	}

	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
