package com.jsfspring.curddemo.mbean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.ExpenseDomain;
import com.jsfspring.curddemo.entity.SalesPaymentsDomain;
import com.jsfspring.curddemo.entity.UnitMasterDomain;
import com.jsfspring.curddemo.repositry.ExpenseRepo;
import com.jsfspring.curddemo.repositry.SalesPaymentRepo;

@Controller("expenseMBean")
@SessionScope
public class ExpenseMBean {
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public ExpenseRepo expenseRepo;
	
	public ExpenseDomain expense;
	public List<ExpenseDomain> expenseList;
	
	String newExpenses="/jsfspring/pages/Invoice/expenses.xhtml";
	
	public ExpenseMBean() {
		expense = new ExpenseDomain();
	}
	
	public void newExpense() {
		expense = new ExpenseDomain();
	}
	
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(newExpenses);
		sukiBaseBean.t=new ExpenseDomain();
		sukiBaseBean.overviewList();
	}
	
	public void saveExpense() {
		boolean flag = false;
		if (sukiBaseBean.validateString(expense.getReason(), "Unit Name"))
			flag = true;
		if (sukiBaseBean.validateInteger(expense.getAmount(), "Unit Name"))
			flag = true;
		if (flag)
			return;
		if (expense.getRowId() > 0) {
			sukiBaseBean.addMessage("Expenses", "Update Successfullly");
		}else {
			sukiBaseBean.addMessage("Expenses", "Saved Successfullly");
		}
		expense=expenseRepo.save(expense);
//		sukiBaseBean.t=expense;
//		sukiBaseBean.overviewList();
}
	
	public void getExpenseActionEvent(ActionEvent event) {
		expense = new ExpenseDomain();
		expense = expenseRepo.findById(sukiBaseBean.actionEvent(event)).get();
	}
	
	public void expenseDelete() {
		expenseRepo.delete(expense);
	}
	
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public SukiBaseBean getSukiBaseBean() {
		return sukiBaseBean;
	}

	public void setSukiBaseBean(SukiBaseBean sukiBaseBean) {
		this.sukiBaseBean = sukiBaseBean;
	}

	public CommonObjects getCommonObjects() {
		return commonObjects;
	}

	public void setCommonObjects(CommonObjects commonObjects) {
		this.commonObjects = commonObjects;
	}

	public ExpenseRepo getExpenseRepo() {
		return expenseRepo;
	}

	public void setExpenseRepo(ExpenseRepo expenseRepo) {
		this.expenseRepo = expenseRepo;
	}

	public ExpenseDomain getExpense() {
		return expense;
	}

	public void setExpense(ExpenseDomain expense) {
		this.expense = expense;
	}

	public List<ExpenseDomain> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<ExpenseDomain> expenseList) {
		this.expenseList = expenseList;
	}

}
