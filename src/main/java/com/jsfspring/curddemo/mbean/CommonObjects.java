package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.jsfspring.curddemo.repositry.ProductUomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.BillTransDomain;
import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.DeliveryChalanDomain;
import com.jsfspring.curddemo.entity.DeliveryChalanMaster;
import com.jsfspring.curddemo.entity.InwardTrans;
import com.jsfspring.curddemo.entity.OverviewListAndCount;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductUnitWise;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchaseBillTrans;
import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.entity.UnitMasterDomain;
import com.jsfspring.curddemo.repositry.ProductRepo;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

/**
 * Session Bean implementation class CommonObjects
 */
@Service
public class CommonObjects{

	/**
	 * Default constructor.
	 */
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	public ProductRepo productRepo;

	@Autowired
	public ProductUomRepo ProductUomRepo;
	
	public CommonObjects() {
		// TODO Auto-generated constructor stub
	}

	List<UnitMasterDomain> getUomMapList() {
		Query query = entityManager.createQuery("from UnitMasterDomain");
		List<UnitMasterDomain> list = query.getResultList();
		return list;
	}

	public List<SupplierDomain> getList(String search) {
		StringBuffer sql = new StringBuffer();
		sql.append("from SupplierDomain where name like '%");
		sql.append(search);
		sql.append("%'");
		Query query = entityManager.createQuery(sql.toString()).setFirstResult(0).setMaxResults(10);
		List<SupplierDomain> resultList = query.getResultList();
		return resultList;
	}

	public List<ProductDomain> getProductList(String search) {
		StringBuffer sql = new StringBuffer();
		sql.append("from ProductDomain where item like '%");
		sql.append(search);
		sql.append("%'");
		Query query = entityManager.createQuery(sql.toString()).setFirstResult(0).setMaxResults(10);
		List<ProductDomain> resultList = query.getResultList();
		return resultList;
	}

	public List<Company> getCompanyList(String search) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Company where compName like '%");
		sql.append(search);
		sql.append("%'");
		Query query = entityManager.createQuery(sql.toString()).setFirstResult(0).setMaxResults(10);
		List<Company> resultList = query.getResultList();
		return resultList;
	}
	
	public OverviewListAndCount getAll(Object ObjectType, int first, int pageSize, String sortField, String sortOrder,
			Map<String, Object> filters) {
		OverviewListAndCount overView=new OverviewListAndCount();
		StringBuffer sql = new StringBuffer();
//		Pageable pageable = PageRequest.of(0, 10);
//		productRepo.findAll(pageable);
		    sql.append(String.format("from %s ", ObjectType.getClass().getSimpleName()));
		if (filters != null && filters.size() > 0) {
			sql.append(" where ");
			sql.append(SukiAppUtil.filterFormat(ObjectType, sortField, sortOrder, filters));
		}
		if(sortField!=null) {
			sql.append(" order by ");
			sql.append(sortField);
			sql.append(" ");
			if(sortOrder.equalsIgnoreCase("ASCENDING"))
				sql.append(sortOrder.substring(0, 3));
			else
				sql.append(sortOrder.substring(0, 4));
		}

		System.out.println(sql.toString());
		Query query = entityManager.createQuery(sql.toString());
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		overView.setOverviewList(query.getResultList());
		sql = new StringBuffer();
		sql.append(String.format(SukiAppConstants.COUNT_QUERY,ObjectType.getClass().getSimpleName()));
		if (filters != null && filters.size() > 0) {
			sql.append(" where ");
			sql.append(SukiAppUtil.filterFormat(ObjectType, null, null, filters));
		}
		System.out.println("count query---"+sql);
		query = entityManager
				.createQuery(sql.toString());
		Long count = (long) query.getSingleResult();
		System.out.println("count"+count);
		overView.setCount(count.intValue());
		return overView;
	}

	public <T> int getCountQuery(T tableName,Map<String, Object> filters) throws SukiException {
		System.out.println("ClassName"+tableName.getClass().getName());
		StringBuffer sql = new StringBuffer();
		sql.append(String.format(SukiAppConstants.COUNT_QUERY,tableName.getClass().getSimpleName()));
		if (filters != null && filters.size() > 0) {
			sql.append(" where ");
			sql.append(SukiAppUtil.filterFormat(tableName, null, null, filters));
		}
		System.out.println("count query---"+sql);
		Query query = entityManager
				.createQuery(sql.toString());
		Long count = (long) query.getSingleResult();
		System.out.println("count"+count);
		return count.intValue();
	}

//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)  
//	public <T> T merge(T object){
//    	return entityManager.merge(object);
//	}
//    
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)  
//	public <T> void remove(T object){
//    	if(!entityManager.contains(object)) {
//    		object=entityManager.merge(object);
//    	}
//    	 entityManager.remove(object);
//	}
	
    @Transactional 
	public void updateBeforeDelete(int billNo){
    	 Query query=entityManager.createNativeQuery("update DC_MASTER SET STATUS='Pending',BILL_NO=NULL where BILL_NO=?");
	     query.setParameter(1, billNo);
	     query.executeUpdate();
	}

	@Transactional
	public <T> T save(T object) throws SukiException {
		if (object.getClass().equals(PurchaseBillMaster.class)) {
			PurchaseBillMaster temp = (PurchaseBillMaster) object;
//			if (temp.getInvoiceType().equalsIgnoreCase("Direct")) {
//				temp.setInwardMaster(new InwardMaster(temp));
//				temp.getInwardMaster().setInwardNo(getAutoNumber("inwardNo","InwardMaster"));
//				System.out.println("Size---"+temp.getInwardMaster().getInwardTransList().size());
//				temp.getPurchaseBillTransList().parallelStream().forEach(i -> {
//					addProductUom(i.getUom(),i.getQty());
//				});
//			}
		}

		if (object.getClass().equals(DeliveryChalanMaster.class)) {
			DeliveryChalanMaster temp = (DeliveryChalanMaster) object;
			temp.getDcTransList().parallelStream().forEach(i -> {
				ProductUom product = i.getUom();
				product.setStock(product.getStock() - i.getNos());
				entityManager.merge(product);
			//	entityManager.persist(i);
			});
		}
//		if (object.getClass().equals(InwardMaster.class)) {
//			InwardMaster temp = (InwardMaster) object;
//			List<Integer> intList=new ArrayList<Integer>();
//			temp.getInwardTransList().parallelStream().forEach(i -> {
//				ProductUom product = i.getUom();
//				product.setStock(product.getStock() + i.getReceived());
//				entityManager.merge(product);
//			//	entityManager.persist(i);
//				PurchaseOrderTrans poTrans=i.getPoTransNo();
//				if(poTrans!=null) {
//				poTrans.setReceived(i.getReceived());
//				if(poTrans.getNos()==poTrans.getReceived())
//					poTrans.setStatus(SukiAppConstants.CLOSED_STATUS);
//				else
//					poTrans.setStatus(SukiAppConstants.PENDING_STATUS);
//				entityManager.merge(poTrans);
//				if(!intList.contains(poTrans.getPoNo().getPoNo())) {
//					intList.add(poTrans.getPoNo().getPoNo());
//					setInwardStatus(poTrans.getPoNo().getPoNo());
//				}
//				}
//			});
//		}
		if (object.getClass().equals(BillMasterDomain.class)) {
			BillMasterDomain temp = (BillMasterDomain) object;
			if (temp.getInvoiceType().equalsIgnoreCase("Direct")) {
				temp.getBillTransList().parallelStream().forEach(i -> {
					ProductUom product = i.getUom();
					product.setStock(product.getStock() - i.getQty());
					System.out.println("product stock"+product.getStock()+" id "+product.getRowId());
					ProductUomRepo.save(product);
//					entityManager.merge(product);
				});
			}
		}
		if (object.getClass().equals(BillTransDomain.class)) {
			BillTransDomain temp = (BillTransDomain) object;
			if (temp.getBillMaster().getInvoiceType().equalsIgnoreCase("Direct")) {
					ProductUom product = temp.getUom();
					product.setStock(product.getStock() - temp.getQty());
					entityManager.merge(product);
			}
		}
		if (object.getClass().equals(PurchaseBillTrans.class)) {
			PurchaseBillTrans temp = (PurchaseBillTrans) object;
			if (temp.getMasterRowId().getInvoiceType().equalsIgnoreCase("Direct")) {
					ProductUom product = temp.getUom();
					product.setStock(product.getStock() + temp.getQty());
					entityManager.merge(product);
			}
		}
		object=entityManager.merge(object);
		return object;
	}

	public <T> T edit(T object, int rowId) throws SukiException {
		T t = (T) entityManager.find(object.getClass(), rowId);
		return t;
	}
	public int getAutoNumber(String fieldName,String tableName)throws SukiException
    {
    	try{
    	Query query=null;
 		StringBuffer queryString=new StringBuffer(" select max(");
 		queryString.append(fieldName);
 		queryString.append(") From ");
 		queryString.append(tableName);
 		
    	System.out.println(" SQL:\n" +queryString.toString());
 		query=entityManager.createQuery(queryString.toString());
 		int rowId=(int) query.getSingleResult();
 		return ++rowId;
    	}catch (Exception e) {
			// TODO: handle exception
    		int rowId=1;
    		return	rowId;
		}
    }
	public <T> T update(T object) throws SukiException {
		if (object.getClass().equals(DeliveryChalanDomain.class)) {
			DeliveryChalanDomain temp = (DeliveryChalanDomain) object;
			ProductUom product;
			if(temp.getUomForEdit()!=null) {
			product = temp.getUomForEdit();
			product.setStock(product.getStock() + temp.getNosForEdit());
			entityManager.merge(product);
			}
			product = temp.getUom();
			product.setStock(product.getStock() - temp.getNos());
			entityManager.merge(product);
		}
//		if (object.getClass().equals(InwardTrans.class)) {
//			InwardTrans temp = (InwardTrans) object;
//			ProductUom product;
//			product = temp.getUom();
//			System.out.println("Stock"+product.getStock());
//			System.out.println("Recived"+temp.getReceived());
//			System.out.println("NOs For Edit"+temp.getNosForEdit());
//			product.setStock(product.getStock() + temp.getReceived()- temp.getNosForEdit());
//			System.out.println("Final Stock"+product.getStock());
//			entityManager.merge(product);
//			PurchaseOrderTrans poTrans=temp.getPoTransNo();
//			poTrans.setReceived(poTrans.getReceived()+temp.getReceived()- temp.getNosForEdit());
//			if(poTrans.getNos()==poTrans.getReceived())
//				poTrans.setStatus(SukiAppConstants.CLOSED_STATUS);
//			else
//				poTrans.setStatus(SukiAppConstants.PENDING_STATUS);
//			entityManager.merge(poTrans);
//			setInwardStatus(poTrans.getPoNo().getPoNo());
//			return object;
//		}
		if (object.getClass().equals(BillTransDomain.class)) {
			BillTransDomain temp = (BillTransDomain) object;
			if (temp.getBillMaster().getInvoiceType().equalsIgnoreCase("Direct")) {
				ProductUom product;
				if(temp.getUomForEdit()!=null) {
				product = temp.getUomForEdit();
				product.setStock(product.getStock() + temp.getNosForEdit());
				entityManager.merge(product);
				}
				product = temp.getUom();
				product.setStock(product.getStock() - temp.getQty());
				entityManager.merge(product);
			}
		}
		if (object.getClass().equals(PurchaseBillTrans.class)) {
			PurchaseBillTrans temp = (PurchaseBillTrans) object;
			if (temp.getMasterRowId().getInvoiceType().equalsIgnoreCase("Direct")) {
				ProductUom product;
				if(temp.getUomForEdit()!=null) {
				product = temp.getUomForEdit();
				product.setStock(product.getStock() - temp.getNosForEdit());
				entityManager.merge(product);
				}
				product = temp.getUom();
				product.setStock(product.getStock() +
						temp.getQty());
				entityManager.merge(product);
				InwardTrans trans=temp.getInwardTransNo();
				trans.setProductId(temp.getProductId());
				trans.setUom(temp.getUom());
				trans.setReceived(temp.getQty());
				entityManager.merge(trans);
			}
		}
		object=entityManager.merge(object);
		return object;
	}

	public <T> void delete(T object, int rowId) throws SukiException {
//		if (object.getClass().equals(InwardMaster.class)) {
//			InwardMaster temp = (InwardMaster) object;
//			List<Integer> intList=new ArrayList<Integer>();
//			temp.getInwardTransList().parallelStream().forEach(i -> {
//				ProductUom product = i.getUom();
//				product.setStock(product.getStock() - i.getReceived());
//				entityManager.merge(product);
//				PurchaseOrderTrans poTrans=i.getPoTransNo();
//				poTrans.setReceived(poTrans.getReceived()-i.getReceived());
//				System.out.println("POTrans   Delete---"+poTrans.getReceived());
//				System.out.println("Received  Delete---"+i.getReceived());
//				if(poTrans.getNos()==poTrans.getReceived())
//					poTrans.setStatus(SukiAppConstants.CLOSED_STATUS);
//				else
//					poTrans.setStatus(SukiAppConstants.PENDING_STATUS);
//				entityManager.merge(poTrans);
//				if(!intList.contains(poTrans.getPoNo().getPoNo())) {
//					intList.add(poTrans.getPoNo().getPoNo());
//					setInwardStatus(poTrans.getPoNo().getPoNo());
//				}
//			});
//		}
		if (object.getClass().equals(PurchaseBillMaster.class)) {
			PurchaseBillMaster temp = (PurchaseBillMaster) object;
			if (temp.getInvoiceType().equalsIgnoreCase("Direct")) {
				temp.getPurchaseBillTransList().parallelStream().forEach(i -> {
					ProductUom product = i.getUom();
					product.setStock(product.getStock() - i.getQty());
					entityManager.merge(product);
				});
			}
		}
		entityManager.remove(entityManager.find(object.getClass(), rowId));
	//	entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
	}
	
	public void addProductUom(ProductUom product,double quantity) {
		product.setStock(product.getStock() + quantity);
		entityManager.merge(product);
	}
	
	public <T> void delete(T object) throws SukiException {
		T object1 = null;
		if (object.getClass().equals(DeliveryChalanDomain.class)) {
			DeliveryChalanDomain temp = (DeliveryChalanDomain) object;
			addProductUom(temp.getUom(),temp.getNos());
		}
		if (object.getClass().equals(DeliveryChalanMaster.class)) {
			DeliveryChalanMaster temp = (DeliveryChalanMaster) object;
			temp.getDcTransList().parallelStream().forEach(i -> {
				addProductUom(i.getUom(),i.getNos());
			});
		}
		if (object.getClass().equals(BillMasterDomain.class)) {
			BillMasterDomain temp = (BillMasterDomain) object;
			if (temp.getInvoiceType().equalsIgnoreCase("Direct")) {
				object1=(T) temp.getDcMaster();
				temp.getBillTransList().parallelStream().forEach(i -> {
					addProductUom(i.getUom(),i.getQty());
				});
			}else {
				temp.getDcMasterList().parallelStream().forEach(i->{
					i.setStatus("Pending");
					i.setBillNo(null);
					entityManager.merge(i);
				});
			}
		}
//		if (object.getClass().equals(InwardTrans.class)) {
//			InwardTrans temp = (InwardTrans) object;
//			ProductUom product = temp.getUom();
//			product.setStock(product.getStock() - temp.getReceived());
//			entityManager.merge(product);
//			PurchaseOrderTrans poTrans=temp.getPoTransNo();
//			poTrans.setReceived(poTrans.getReceived()-temp.getReceived());
//			if(poTrans.getNos()==poTrans.getReceived())
//				poTrans.setStatus(SukiAppConstants.CLOSED_STATUS);
//			else
//				poTrans.setStatus(SukiAppConstants.PENDING_STATUS);
//			entityManager.merge(poTrans);
//			setInwardStatus(poTrans.getPoNo().getPoNo());
//		}
		if (object.getClass().equals(BillTransDomain.class)) {
			BillTransDomain temp = (BillTransDomain) object;
			if (temp.getBillMaster().getInvoiceType().equalsIgnoreCase("Direct")) {
				ProductUom product = temp.getUom();
				product.setStock(product.getStock() + temp.getQty());
				entityManager.merge(product);
			}
		}
		if (object.getClass().equals(PurchaseBillTrans.class)) {
			PurchaseBillTrans temp = (PurchaseBillTrans) object;
			if (temp.getMasterRowId().getInvoiceType().equalsIgnoreCase("Direct")) {
				ProductUom product = temp.getUom();
				product.setStock(product.getStock() - temp.getQty());
				entityManager.merge(product);
			}
		}
	    entityManager.remove(object);
		// PurchaseBillTrans temp = (PurchaseBillTrans) object;
		// entityManager.remove(entityManager.find(object.getClass(), temp.getRowId()));
//		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		System.out.println("Removed-----");
		if(object1!=null)
		entityManager.remove(object1);
	}
	
	
	public void setInwardStatus(int masterId) {
		Query query=entityManager.createNativeQuery("select count(STATUS) FROM PURCHASE_ORDER_TRANS WHERE STATUS ='pending' and PO_NO=?");
		query.setParameter(1, masterId);
		int count=(int) query.getSingleResult();
		if(count>0) {
			 query=entityManager.createNativeQuery("UPDATE PURCHASE_ORDER_MASTER SET STATUS ='Pending' WHERE PO_NO=?");
		     query.setParameter(1, masterId);
		     query.executeUpdate();
		}else {
			 query=entityManager.createNativeQuery("UPDATE PURCHASE_ORDER_MASTER SET STATUS ='Closed' WHERE PO_NO=?");
		     query.setParameter(1, masterId);
		     query.executeUpdate();
		}
	}
	public List<ProductUnitWise> getUnitWiseProductInwardOutward(int month,int year,String type) {
		String sql="select B.ITEM,SUM(A.NOS) AS NOS,C.ROW_ID,D.UNIT_NAME,SUM(A.NOS*A.PRICE) AS AMOUNT from VW_PRODUCT A, PRODUCT B, PRODUCT_UOM C,UNIT_MASTER D WHERE A.TYPE='%s' AND A.ITEMS=B.PROD_CODE AND A.UOM_ID=C.ROW_ID AND C.ROW_ID=D.ROW_ID AND month(date)=? AND year(date)=? GROUP BY B.ITEM,D.UNIT_NAME,C.ROW_ID ORDER BY B.ITEM";
		sql=sql.format(sql, type);
		Query query=entityManager.createNativeQuery(sql);
		query.setParameter(1, month);
		query.setParameter(2, year);
		List<Object[]> list=query.getResultList();
		ProductUnitWise obj=new ProductUnitWise();
		List<ProductUnitWise> productList=new ArrayList<ProductUnitWise>();
		for(int i=0;i<list.size();i++) {
			obj=new ProductUnitWise();
			obj.setProduct((String)list.get(i)[0]);
			obj.setNos(Double.parseDouble(list.get(i)[1].toString()));
			obj.setRowId((int)list.get(i)[2]);
			obj.setUnitName((String)list.get(i)[3]);
			obj.setAmount(Double.parseDouble(list.get(i)[4].toString()));
			productList.add(obj);
		}
		return productList;
	}
//	public double getTotalProfit() {
//		String sql = "select (sum(QUANTITY*RATE)-sum(QUANTITY*PUR_RATE)) as Rate from BILL_TRANS";
//		Query query=entityManager.createNativeQuery(sql);
//		double salesProfit=(double) query.getSingleResult();
//		sql = "Select sum(GST_AMOUNT) from BILL_MASTER";
//		query=entityManager.createNativeQuery(sql);
//		double salesGst=(double) query.getSingleResult();
//		sql = "Select sum(GST_AMOUNT) from PURCHASE_BILL_MASTER";
//		query=entityManager.createNativeQuery(sql);
//		double purchaseGst=(double) query.getSingleResult();
//	}
}
