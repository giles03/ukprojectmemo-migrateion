package com.sonybmg.struts.pmemo3.model;

import java.util.Date;

import com.sonybmg.struts.pmemo3.form.DigitalForm;


public class DashboardReportNew {
	
	private String pmemoFormat;
	private String formatType;
	private Date pmemoReleaseDate;
	private Date pmemoPreOrderDate;	
	private String catItemId;
	private String artist;
	private String title;
	private Date dashReleaseDate;
	private Date dashPreOrderDate;
	private String repOwner;
	//private String repOwnerCountry;
	private String initiatorName;
	private String cfg;
	
	/* Preparation */
	private Date packagingPlanDate;
	private Date packagingActualDate;
	private String packagingImage;	
	private Date gloresScheduledPlanDate;
	private Date gloresScheduledActualDate;	
	private String gloresScheduledImage;	
	private Date gloresForecastPlanDate;
	private Date gloresForecastActualDate;	
	private String gloresForecastImage;	
	private String preparationOverallFlag;
	
	/* Label Copy */	
	private Date labelCopyPlanDate;
	private Date labelCopyActualDate;
	private String labelCopyImage;
	private String labelCopyOverallFlag;	
	
	/* Digital Rights */
	private Date digitalRightsClearedPlanDate;	
	private Date digitalRightsClearedActualDate;
	private String digitalRightsClearedImage;
	private String digitalRightsOverallFlag;	
	
	/* Parts */
	private Date approveProdMasterPlanDate;
	private Date approveProdMasterActualDate;
	private String approveProdMasterImage;	
	
	private Date approveProdArtworkPlanDate;
	private Date approveProdArtworkActualDate;
	private String approveProdArtworkImage;	
	
	private Date instructionToPrepareAOMAPlanDate;
	private Date instructionToPrepareAOMAActualDate;	
	private String instructionToPrepareAOMAImage;	
	
	private Date aOMAMasterRegPlanDate;	
	private Date aOMAMasterRegActualDate;
	private String aOMAMasterRegImage;	
	
	private Date aOMAArtworkRegPlanDate;	
	private Date aOMAArtworkRegActualDate;
	private String aOMAArtworkRegImage;	
	
	private Date productionReadyPlanDate;	
	private Date productionReadyActualDate;
	private String productionReadyImage;
	private String partsOverallFlag;	
	
	/* Mobile */
	private Date mMDSCompletePlanDate;
	private Date mMDSCompleteActualDate;		
	private String mMDSCompleteImage;
	private String mobileOverallFlag;

	/* Initial Orders */	
	private Date manufOrderPlacedPlanDate;	
	private Date manufOrderPlacedActualDate;
	private String manufOrderPlacedImage;	
	private Date qtySheetPlanDate;
	private Date qtySheetActualDate;
	private String qtySheetImage;
	private Date manufOrderShippedPlanDate;	
	private Date manufOrderShippedActualDate;
	private String manufOrderShippedImage;			
	private Date manufOrderAtDistPlanDate;	
	private Date manufOrderAtDistActualDate;
	private String manufOrderAtDistImage;	
	private String ordersOverallFlag;

	private String unattachedReportFlag;

	public Date getAOMAArtworkRegActualDate() {
		return aOMAArtworkRegActualDate;
	}

	public void setAOMAArtworkRegActualDate(Date artworkRegActualDate) {
		aOMAArtworkRegActualDate = artworkRegActualDate;
	}

	public String getAOMAArtworkRegImage() {
		return aOMAArtworkRegImage;
	}

	public void setAOMAArtworkRegImage(String artworkRegImage) {
		aOMAArtworkRegImage = artworkRegImage;
	}

	public Date getAOMAArtworkRegPlanDate() {
		return aOMAArtworkRegPlanDate;
	}

	public void setAOMAArtworkRegPlanDate(Date artworkRegPlanDate) {
		aOMAArtworkRegPlanDate = artworkRegPlanDate;
	}

	public Date getAOMAMasterRegActualDate() {
		return aOMAMasterRegActualDate;
	}

	public void setAOMAMasterRegActualDate(Date masterRegActualDate) {
		aOMAMasterRegActualDate = masterRegActualDate;
	}

	public String getAOMAMasterRegImage() {
		return aOMAMasterRegImage;
	}

	public void setAOMAMasterRegImage(String masterRegImage) {
		aOMAMasterRegImage = masterRegImage;
	}

	public Date getAOMAMasterRegPlanDate() {
		return aOMAMasterRegPlanDate;
	}

	public void setAOMAMasterRegPlanDate(Date masterRegPlanDate) {
		aOMAMasterRegPlanDate = masterRegPlanDate;
	}

	public Date getApproveProdArtworkActualDate() {
		return approveProdArtworkActualDate;
	}

	public void setApproveProdArtworkActualDate(Date approveProdArtworkActualDate) {
		this.approveProdArtworkActualDate = approveProdArtworkActualDate;
	}

	public String getApproveProdArtworkImage() {
		return approveProdArtworkImage;
	}

	public void setApproveProdArtworkImage(String approveProdArtworkImage) {
		this.approveProdArtworkImage = approveProdArtworkImage;
	}

	public Date getApproveProdArtworkPlanDate() {
		return approveProdArtworkPlanDate;
	}

	public void setApproveProdArtworkPlanDate(Date approveProdArtworkPlanDate) {
		this.approveProdArtworkPlanDate = approveProdArtworkPlanDate;
	}

	public Date getApproveProdMasterActualDate() {
		return approveProdMasterActualDate;
	}

	public void setApproveProdMasterActualDate(Date approveProdMasterActualDate) {
		this.approveProdMasterActualDate = approveProdMasterActualDate;
	}

	public String getApproveProdMasterImage() {
		return approveProdMasterImage;
	}

	public void setApproveProdMasterImage(String approveProdMasterImage) {
		this.approveProdMasterImage = approveProdMasterImage;
	}

	public Date getApproveProdMasterPlanDate() {
		return approveProdMasterPlanDate;
	}

	public void setApproveProdMasterPlanDate(Date approveProdMasterPlanDate) {
		this.approveProdMasterPlanDate = approveProdMasterPlanDate;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getCatItemId() {
		return catItemId;
	}

	public void setCatItemId(String catItemId) {
		this.catItemId = catItemId;
	}

	public String getCfg() {
		return cfg;
	}

	public void setCfg(String cfg) {
		this.cfg = cfg;
	}

	public Date getDashPreOrderDate() {
		return dashPreOrderDate;
	}

	public void setDashPreOrderDate(Date dashPreOrderDate) {
		this.dashPreOrderDate = dashPreOrderDate;
	}

	public Date getDashReleaseDate() {
		return dashReleaseDate;
	}

	public void setDashReleaseDate(Date dashReleaseDate) {
		this.dashReleaseDate = dashReleaseDate;
	}

	public Date getDigitalRightsClearedActualDate() {
		return digitalRightsClearedActualDate;
	}

	public void setDigitalRightsClearedActualDate(
			Date digitalRightsClearedActualDate) {
		this.digitalRightsClearedActualDate = digitalRightsClearedActualDate;
	}

	public String getDigitalRightsClearedImage() {
		return digitalRightsClearedImage;
	}

	public void setDigitalRightsClearedImage(String digitalRightsClearedImage) {
		this.digitalRightsClearedImage = digitalRightsClearedImage;
	}

	public Date getDigitalRightsClearedPlanDate() {
		return digitalRightsClearedPlanDate;
	}

	public void setDigitalRightsClearedPlanDate(Date digitalRightsClearedPlanDate) {
		this.digitalRightsClearedPlanDate = digitalRightsClearedPlanDate;
	}

	public String getDigitalRightsOverallFlag() {
		return digitalRightsOverallFlag;
	}

	public void setDigitalRightsOverallFlag(String digitalRightsOverallFlag) {
		this.digitalRightsOverallFlag = digitalRightsOverallFlag;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public Date getGloresForecastActualDate() {
		return gloresForecastActualDate;
	}

	public void setGloresForecastActualDate(Date gloresForecastActualDate) {
		this.gloresForecastActualDate = gloresForecastActualDate;
	}

	public String getGloresForecastImage() {
		return gloresForecastImage;
	}

	public void setGloresForecastImage(String gloresForecastImage) {
		this.gloresForecastImage = gloresForecastImage;
	}

	public Date getGloresForecastPlanDate() {
		return gloresForecastPlanDate;
	}

	public void setGloresForecastPlanDate(Date gloresForecastPlanDate) {
		this.gloresForecastPlanDate = gloresForecastPlanDate;
	}

	public Date getGloresScheduledActualDate() {
		return gloresScheduledActualDate;
	}

	public void setGloresScheduledActualDate(Date gloresScheduledActualDate) {
		this.gloresScheduledActualDate = gloresScheduledActualDate;
	}

	public String getGloresScheduledImage() {
		return gloresScheduledImage;
	}

	public void setGloresScheduledImage(String gloresScheduledImage) {
		this.gloresScheduledImage = gloresScheduledImage;
	}

	public Date getGloresScheduledPlanDate() {
		return gloresScheduledPlanDate;
	}

	public void setGloresScheduledPlanDate(Date gloresScheduledPlanDate) {
		this.gloresScheduledPlanDate = gloresScheduledPlanDate;
	}

	public String getManufOrderAtDistImage() {
		return manufOrderAtDistImage;
	}

	public void setManufOrderAtDistImage(String manufOrderAtDistImage) {
		this.manufOrderAtDistImage = manufOrderAtDistImage;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}

	public Date getInstructionToPrepareAOMAActualDate() {
		return instructionToPrepareAOMAActualDate;
	}

	public void setInstructionToPrepareAOMAActualDate(
			Date instructionToPrepareAOMAActualDate) {
		this.instructionToPrepareAOMAActualDate = instructionToPrepareAOMAActualDate;
	}

	public String getInstructionToPrepareAOMAImage() {
		return instructionToPrepareAOMAImage;
	}

	public void setInstructionToPrepareAOMAImage(
			String instructionToPrepareAOMAImage) {
		this.instructionToPrepareAOMAImage = instructionToPrepareAOMAImage;
	}

	public Date getInstructionToPrepareAOMAPlanDate() {
		return instructionToPrepareAOMAPlanDate;
	}

	public void setInstructionToPrepareAOMAPlanDate(
			Date instructionToPrepareAOMAPlanDate) {
		this.instructionToPrepareAOMAPlanDate = instructionToPrepareAOMAPlanDate;
	}

	public Date getLabelCopyActualDate() {
		return labelCopyActualDate;
	}

	public void setLabelCopyActualDate(Date labelCopyActualDate) {
		this.labelCopyActualDate = labelCopyActualDate;
	}

	public String getLabelCopyImage() {
		return labelCopyImage;
	}

	public void setLabelCopyImage(String labelCopyImage) {
		this.labelCopyImage = labelCopyImage;
	}

	public String getLabelCopyOverallFlag() {
		return labelCopyOverallFlag;
	}

	public void setLabelCopyOverallFlag(String labelCopyOverallFlag) {
		this.labelCopyOverallFlag = labelCopyOverallFlag;
	}

	public Date getLabelCopyPlanDate() {
		return labelCopyPlanDate;
	}

	public void setLabelCopyPlanDate(Date labelCopyPlanDate) {
		this.labelCopyPlanDate = labelCopyPlanDate;
	}

	public Date getManufOrderAtDistActualDate() {
		return manufOrderAtDistActualDate;
	}

	public void setManufOrderAtDistActualDate(Date manufOrderAtDistActualDate) {
		this.manufOrderAtDistActualDate = manufOrderAtDistActualDate;
	}

	public Date getManufOrderAtDistPlanDate() {
		return manufOrderAtDistPlanDate;
	}

	public void setManufOrderAtDistPlanDate(Date manufOrderAtDistPlanDate) {
		this.manufOrderAtDistPlanDate = manufOrderAtDistPlanDate;
	}

	public Date getManufOrderPlacedActualDate() {
		return manufOrderPlacedActualDate;
	}

	public void setManufOrderPlacedActualDate(Date manufOrderPlacedActualDate) {
		this.manufOrderPlacedActualDate = manufOrderPlacedActualDate;
	}

	public String getManufOrderPlacedImage() {
		return manufOrderPlacedImage;
	}

	public void setManufOrderPlacedImage(String manufOrderPlacedImage) {
		this.manufOrderPlacedImage = manufOrderPlacedImage;
	}

	public Date getManufOrderPlacedPlanDate() {
		return manufOrderPlacedPlanDate;
	}

	public void setManufOrderPlacedPlanDate(Date manufOrderPlacedPlanDate) {
		this.manufOrderPlacedPlanDate = manufOrderPlacedPlanDate;
	}

	public Date getManufOrderShippedActualDate() {
		return manufOrderShippedActualDate;
	}

	public void setManufOrderShippedActualDate(Date manufOrderShippedActualDate) {
		this.manufOrderShippedActualDate = manufOrderShippedActualDate;
	}

	public String getManufOrderShippedImage() {
		return manufOrderShippedImage;
	}

	public void setManufOrderShippedImage(String manufOrderShippedImage) {
		this.manufOrderShippedImage = manufOrderShippedImage;
	}

	public Date getManufOrderShippedPlanDate() {
		return manufOrderShippedPlanDate;
	}

	public void setManufOrderShippedPlanDate(Date manufOrderShippedPlanDate) {
		this.manufOrderShippedPlanDate = manufOrderShippedPlanDate;
	}

	public Date getMMDSCompleteActualDate() {
		return mMDSCompleteActualDate;
	}

	public void setMMDSCompleteActualDate(Date completeActualDate) {
		mMDSCompleteActualDate = completeActualDate;
	}

	public String getMMDSCompleteImage() {
		return mMDSCompleteImage;
	}

	public void setMMDSCompleteImage(String completeImage) {
		mMDSCompleteImage = completeImage;
	}

	public Date getMMDSCompletePlanDate() {
		return mMDSCompletePlanDate;
	}

	public void setMMDSCompletePlanDate(Date completePlanDate) {
		mMDSCompletePlanDate = completePlanDate;
	}

	public String getMobileOverallFlag() {
		return mobileOverallFlag;
	}

	public void setMobileOverallFlag(String mobileOverallFlag) {
		this.mobileOverallFlag = mobileOverallFlag;
	}

	public String getOrdersOverallFlag() {
		return ordersOverallFlag;
	}

	public void setOrdersOverallFlag(String ordersOverallFlag) {
		this.ordersOverallFlag = ordersOverallFlag;
	}

	public Date getPackagingActualDate() {
		return packagingActualDate;
	}

	public void setPackagingActualDate(Date packagingActualDate) {
		this.packagingActualDate = packagingActualDate;
	}

	public String getPackagingImage() {
		return packagingImage;
	}

	public void setPackagingImage(String packagingImage) {
		this.packagingImage = packagingImage;
	}

	public Date getPackagingPlanDate() {
		return packagingPlanDate;
	}

	public void setPackagingPlanDate(Date packagingPlanDate) {
		this.packagingPlanDate = packagingPlanDate;
	}

	public String getPartsOverallFlag() {
		return partsOverallFlag;
	}

	public void setPartsOverallFlag(String partsOverallFlag) {
		this.partsOverallFlag = partsOverallFlag;
	}

	public String getPmemoFormat() {
		return pmemoFormat;
	}

	public void setPmemoFormat(String pmemoFormat) {
		this.pmemoFormat = pmemoFormat;
	}

	public Date getPmemoPreOrderDate() {
		return pmemoPreOrderDate;
	}

	public void setPmemoPreOrderDate(Date pmemoPreOrderDate) {
		this.pmemoPreOrderDate = pmemoPreOrderDate;
	}

	public Date getPmemoReleaseDate() {
		return pmemoReleaseDate;
	}

	public void setPmemoReleaseDate(Date pmemoReleaseDate) {
		this.pmemoReleaseDate = pmemoReleaseDate;
	}

	public String getPreparationOverallFlag() {
		return preparationOverallFlag;
	}

	public void setPreparationOverallFlag(String preparationOverallFlag) {
		this.preparationOverallFlag = preparationOverallFlag;
	}

	public Date getProductionReadyActualDate() {
		return productionReadyActualDate;
	}

	public void setProductionReadyActualDate(Date productionReadyActualDate) {
		this.productionReadyActualDate = productionReadyActualDate;
	}

	public String getProductionReadyImage() {
		return productionReadyImage;
	}

	public void setProductionReadyImage(String productionReadyImage) {
		this.productionReadyImage = productionReadyImage;
	}

	public Date getProductionReadyPlanDate() {
		return productionReadyPlanDate;
	}

	public void setProductionReadyPlanDate(Date productionReadyPlanDate) {
		this.productionReadyPlanDate = productionReadyPlanDate;
	}

	public Date getQtySheetActualDate() {
		return qtySheetActualDate;
	}

	public void setQtySheetActualDate(Date qtySheetActualDate) {
		this.qtySheetActualDate = qtySheetActualDate;
	}

	public String getQtySheetImage() {
		return qtySheetImage;
	}

	public void setQtySheetImage(String qtySheetImage) {
		this.qtySheetImage = qtySheetImage;
	}

	public Date getQtySheetPlanDate() {
		return qtySheetPlanDate;
	}

	public void setQtySheetPlanDate(Date qtySheetPlanDate) {
		this.qtySheetPlanDate = qtySheetPlanDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUnattachedReportFlag() {
		return unattachedReportFlag;
	}

	public void setUnattachedReportFlag(String unattachedReportFlag) {
		this.unattachedReportFlag = unattachedReportFlag;
	}

	public String getRepOwner() {
		return repOwner;
	}

	public void setRepOwner(String repOwner) {
		this.repOwner = repOwner;
	}
	
	
	

	
	
}
