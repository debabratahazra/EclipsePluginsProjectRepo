package com.excel.model;

public class ResultModel implements Comparable<ResultModel> {
	
	private int index;
	private String postn;
	private String cusip;
	private String instmt_id;
	private String trade_id;

	private String notionalAmount;
	private String struc;
	private String swptnType;
	private String swptnSetmtDate;
	private String lockoutEndDate;
	private String optnMtyDate;
	private String terminationSetmtDate;
	private String expirationDate;
	private String physicalDeliveryDate;
	private String sec481Basis;
	private String cashPaidRecToEnterSwaptn;
	private String cyMTM;
	private String pyMTM;
	private String cyMTMChange;
	private String boyMTMGainToAmort;
	private String eoyMTMGainToAmort;
	private String boyCumMTMGainAmort;
	private String cyMTMGainAmort;
	private String eoyCumMTMGainAmort;
	private String eoyUnrealizedMTMGain;
	private String boyTaxBasis;
	private String eoyTaxBasis;
	private String terminationTaxBasis;
	private String expirationTaxBasis;
	private String deliveryTaxBasis;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPostn() {
		return postn;
	}

	public void setPostn(String postn) {
		this.postn = postn;
	}

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getInstmt_id() {
		return instmt_id;
	}

	public void setInstmt_id(String instmt_id) {
		this.instmt_id = instmt_id;
	}

	public long getTrade_id() {
		return Long.parseLong(trade_id);
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}

	public String getNotionalAmount() {
		return notionalAmount;
	}

	public void setNotionalAmount(String notionalAmount) {
		this.notionalAmount = notionalAmount;
	}

	public String getStruc() {
		return struc;
	}

	public void setStruc(String struc) {
		this.struc = struc;
	}

	public String getSwptnType() {
		return swptnType;
	}

	public void setSwptnType(String swptnType) {
		this.swptnType = swptnType;
	}

	public String getSwptnSetmtDate() {
		return swptnSetmtDate;
	}

	public void setSwptnSetmtDate(String swptnSetmtDate) {
		this.swptnSetmtDate = swptnSetmtDate;
	}

	public String getLockoutEndDate() {
		return lockoutEndDate;
	}

	public void setLockoutEndDate(String lockoutEndDate) {
		this.lockoutEndDate = lockoutEndDate;
	}

	public String getOptnMtyDate() {
		return optnMtyDate;
	}

	public void setOptnMtyDate(String optnMtyDate) {
		this.optnMtyDate = optnMtyDate;
	}

	public String getTerminationSetmtDate() {
		return terminationSetmtDate;
	}

	public void setTerminationSetmtDate(String terminationSetmtDate) {
		this.terminationSetmtDate = terminationSetmtDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPhysicalDeliveryDate() {
		return physicalDeliveryDate;
	}

	public void setPhysicalDeliveryDate(String physicalDeliveryDate) {
		this.physicalDeliveryDate = physicalDeliveryDate;
	}

	public String getSec481Basis() {
		return sec481Basis;
	}

	public void setSec481Basis(String sec481Basis) {
		this.sec481Basis = sec481Basis;
	}

	public String getCashPaidRecToEnterSwaptn() {
		return cashPaidRecToEnterSwaptn;
	}

	public void setCashPaidRecToEnterSwaptn(String cashPaidRecToEnterSwaptn) {
		this.cashPaidRecToEnterSwaptn = cashPaidRecToEnterSwaptn;
	}

	public String getCyMTM() {
		return cyMTM;
	}

	public void setCyMTM(String cyMTM) {
		this.cyMTM = cyMTM;
	}

	public String getPyMTM() {
		return pyMTM;
	}

	public void setPyMTM(String pyMTM) {
		this.pyMTM = pyMTM;
	}

	public String getCyMTMChange() {
		return cyMTMChange;
	}

	public void setCyMTMChange(String cyMTMChange) {
		this.cyMTMChange = cyMTMChange;
	}

	public String getBoyMTMGainToAmort() {
		return boyMTMGainToAmort;
	}

	public void setBoyMTMGainToAmort(String boyMTMGainToAmort) {
		this.boyMTMGainToAmort = boyMTMGainToAmort;
	}

	public String getEoyMTMGainToAmort() {
		return eoyMTMGainToAmort;
	}

	public void setEoyMTMGainToAmort(String eoyMTMGainToAmort) {
		this.eoyMTMGainToAmort = eoyMTMGainToAmort;
	}

	public String getBoyCumMTMGainAmort() {
		return boyCumMTMGainAmort;
	}

	public void setBoyCumMTMGainAmort(String boyCumMTMGainAmort) {
		this.boyCumMTMGainAmort = boyCumMTMGainAmort;
	}

	public String getCyMTMGainAmort() {
		return cyMTMGainAmort;
	}

	public void setCyMTMGainAmort(String cyMTMGainAmort) {
		this.cyMTMGainAmort = cyMTMGainAmort;
	}

	public String getEoyCumMTMGainAmort() {
		return eoyCumMTMGainAmort;
	}

	public void setEoyCumMTMGainAmort(String eoyCumMTMGainAmort) {
		this.eoyCumMTMGainAmort = eoyCumMTMGainAmort;
	}

	public String getEoyUnrealizedMTMGain() {
		return eoyUnrealizedMTMGain;
	}

	public void setEoyUnrealizedMTMGain(String eoyUnrealizedMTMGain) {
		this.eoyUnrealizedMTMGain = eoyUnrealizedMTMGain;
	}

	public String getBoyTaxBasis() {
		return boyTaxBasis;
	}

	public void setBoyTaxBasis(String boyTaxBasis) {
		this.boyTaxBasis = boyTaxBasis;
	}

	public String getEoyTaxBasis() {
		return eoyTaxBasis;
	}

	public void setEoyTaxBasis(String eoyTaxBasis) {
		this.eoyTaxBasis = eoyTaxBasis;
	}

	public String getTerminationTaxBasis() {
		return terminationTaxBasis;
	}

	public void setTerminationTaxBasis(String terminationTaxBasis) {
		this.terminationTaxBasis = terminationTaxBasis;
	}

	public String getExpirationTaxBasis() {
		return expirationTaxBasis;
	}

	public void setExpirationTaxBasis(String expirationTaxBasis) {
		this.expirationTaxBasis = expirationTaxBasis;
	}

	public String getDeliveryTaxBasis() {
		return deliveryTaxBasis;
	}

	public void setDeliveryTaxBasis(String deliveryTaxBasis) {
		this.deliveryTaxBasis = deliveryTaxBasis;
	}

	@Override
	public int compareTo(ResultModel resultModel) {
		return this.cusip.compareTo(resultModel.cusip);
	}
}
