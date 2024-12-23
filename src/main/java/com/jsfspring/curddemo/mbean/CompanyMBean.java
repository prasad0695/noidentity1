package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.CompanyCategory;
import com.jsfspring.curddemo.entity.CompanyCategoryList;
import com.jsfspring.curddemo.repositry.CompanyCategoryListRepo;
import com.jsfspring.curddemo.repositry.CompanyCategoryRepo;
import com.jsfspring.curddemo.repositry.CompanyRepo;


@Controller("companyMBean")
@SessionScope
public class CompanyMBean{
	
	
	@Autowired
	public CompanyRepo companyRepo;
	
	@Autowired
	public CompanyCategoryRepo companyCategoryRepo;
	
	@Autowired
	public CompanyCategoryListRepo companyCategoryListRepo;
	
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
			CompanyCategoryList companyCategoryList;
			CompanyCategory companyCategory;
//			System.out.println(company.getCategoryList());
//			companyCategoryListRepo.deleteAll(company.getCategoryList());
//			company.setCategoryList(new ArrayList<CompanyCategoryList>());
//			for(int i=0;i<company.getCategoryListString().size(); i++) {
//				companyCategoryList = new CompanyCategoryList();
//				companyCategory = companyCategoryRepo.findByCategory(company.getCategoryListString().get(i));
//				companyCategoryList.setCategoryId(companyCategory);
//				companyCategoryList.setCompId(company);
//				company.getCategoryList().add(companyCategoryList);
//			}
//			company.getCategoryListString().stream().forEach(i->{
//				CompanyCategoryList companyCategoryList = new CompanyCategoryList();
//				CompanyCategory companyCategory = companyCategoryRepo.findByCategory(i);
//				companyCategoryList.setCategoryId(companyCategory);
//				companyCategoryList.setCompId(company);
//				company.getCategoryList().add(companyCategoryList);
//				});
			company=companyRepo.save(company);
//			if(company.getCategoryList()!=null && company.getCategoryList().size()>0)
//				company.setCategoryListString(company.getCategoryList().stream().map(i->i.getCategoryId().getCategory()).collect(Collectors.toList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCompanyObjActionEvent(ActionEvent event) {
		company = new Company();
		company = companyRepo.findById(sukiBaseBean.actionEvent(event)).get();
//		if(company.getCategoryList()!=null && company.getCategoryList().size()>0)
//			company.setCategoryListString(company.getCategoryList().stream().map(i->i.getCategoryId().getCategory()).collect(Collectors.toList()));
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
