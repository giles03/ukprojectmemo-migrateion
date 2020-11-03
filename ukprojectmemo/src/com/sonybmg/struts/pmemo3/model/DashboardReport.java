package com.sonybmg.struts.pmemo3.model;

import java.util.Date;

import com.sonybmg.struts.pmemo3.form.DigitalForm;


public class DashboardReport {
	
	private String pmemoFormat;
	private String formatType;
	private Date pmemoReleaseDate;
	private String catItemId;
	private String artist;
	private String title;
	private Date releaseDate;
	private Date localReleaseDate;
	private Date digitalReleaseDate;	
	private Date releaseDateSort;
	private String repOwner;
	private String repOwnerCountry;
	private String initiatorName;
	private String cfg;
	private Date labelCopyPlanDate;
	private Date labelCopyActualDate;
	private String labelCopyImage;
	private Date gloresForecastPlanDate;
	private Date gloresForecastActualDate;	
	private String gloresForecastImage;
	private Date prodOnEOMPlanDate;
	private Date prodOnEOMActualDate;		
	private String prodOnEOMImage;
	private Date approveProdMasterPlanDate;
	private Date approveProdMasterActualDate;
	private String approveProdMasterImage;
	private Date approveProdArtworkPlanDate;
	private Date approveProdArtworkActualDate;
	private String approveProdArtworkImage;
	private Date qtySheetPlanDate;
	private Date qtySheetActualDate;
	private String qtySheetImage;	
	private Date packagingPlanDate;
	private Date packagingActualDate;
	private String packagingImage;
	private Date allInitOrdersRcvdPlanDate;
	private Date allInitOrdersRcvdActualDate;	
	private String allInitOrdersRcvdImage;
	private Date digitalRightsClearedPlanDate;	
	private Date digitalRightsClearedActualDate;
	private String digitalRightsClearedImage;
	private Date instructionToPrepareAOMAPlanDate;
	private Date instructionToPrepareAOMAActualDate;	
	private String instructionToPrepareAOMAImage;	
	private Date successfulAOMARegistrationPlanDate;	
	private Date successfulAOMARegistrationActualDate;
	private String successfulAOMARegistrationImage;
	private Date productionReadyPlanDate;	
	private Date productionReadyActualDate;
	private String productionReadyImage;
	private Date initialManufOrderShippedPlanDate;	
	private Date initialManufOrderShippedActualDate;
	private String initialManufOrderShippedImage;	
	private Date initialOrderAtManufacturerPlanDate;	
	private Date initialOrderAtManufacturerActualDate;
	private String initialOrderAtManufacturerImage;
	private Date prodReadyForDigiDistbnPlanDate;	
	private Date prodReadyForDigiDistbnActualDate;
	private String prodReadyForDigiDistbnImage;	
	private String partsOverallFlag;
	private String scheduleOverallFlag;
	private String ordersOverallFlag;
	private String unattachedReportFlag;
	private String preparationOverallFlag;
	private String labelCopyOverallFlag;
	private String digitalRightsOverallFlag;
	private String digitalSchedulingOverallFlag;
	
	
	
	public String getPmemoFormat() {
		return pmemoFormat;
	}
	public void setPmemoFormat(String pmemoFormat) {
		this.pmemoFormat = pmemoFormat;
	}
	
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public Date getPmemoReleaseDate() {
		return pmemoReleaseDate;
	}
	public void setPmemoReleaseDate(Date pmemoReleaseDate) {
		this.pmemoReleaseDate = pmemoReleaseDate;
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
	public Date getDigitalReleaseDate() {
		return digitalReleaseDate;
	}
	public void setDigitalReleaseDate(Date digitalReleaseDate) {
		this.digitalReleaseDate = digitalReleaseDate;
	}	
	public Date getReleaseDateSort() {
		return releaseDateSort;
	}
	public void setReleaseDateSort(Date releaseDateSort) {
		this.releaseDateSort = releaseDateSort;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
		
	public String getCfg() {
		return cfg;
	}
	public void setCfg(String cfg) {
		this.cfg = cfg;
	}
	public Date getLocalReleaseDate() {
		return localReleaseDate;
	}
	public void setLocalReleaseDate(Date localReleaseDate) {
		this.localReleaseDate = localReleaseDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getRepOwner() {
		return repOwner;
	}
	public void setRepOwner(String repOwner) {
		this.repOwner = repOwner;
	}
	public String getRepOwnerCountry() {
		return repOwnerCountry;
	}
	public void setRepOwnerCountry(String repOwnerCountry) {
		this.repOwnerCountry = repOwnerCountry;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getLabelCopyActualDate() {
		return labelCopyActualDate;
	}
	public void setLabelCopyActualDate(Date labelCopyActualDate) {
		this.labelCopyActualDate = labelCopyActualDate;
	}
	public Date getLabelCopyPlanDate() {
		return labelCopyPlanDate;
	}
	public void setLabelCopyPlanDate(Date labelCopyPlanDate) {
		this.labelCopyPlanDate = labelCopyPlanDate;
	}
	public String getLabelCopyImage() {
		return labelCopyImage;
	}
	public void setLabelCopyImage(String labelCopyImage) {
		this.labelCopyImage = labelCopyImage;
	}
	public Date getGloresForecastActualDate() {
		return gloresForecastActualDate;
	}
	public void setGloresForecastActualDate(Date gloresForecastActualDate) {
		this.gloresForecastActualDate = gloresForecastActualDate;
	}
	public Date getGloresForecastPlanDate() {
		return gloresForecastPlanDate;
	}
	public void setGloresForecastPlanDate(Date gloresForecastPlanDate) {
		this.gloresForecastPlanDate = gloresForecastPlanDate;
	}
		
	public String getGloresForecastImage() {
		return gloresForecastImage;
	}
	public void setGloresForecastImage(String gloresForecastImage) {
		this.gloresForecastImage = gloresForecastImage;
	}
	public Date getAllInitOrdersRcvdActualDate() {
		return allInitOrdersRcvdActualDate;
	}
	public void setAllInitOrdersRcvdActualDate(Date allInitOrdersRcvdActualDate) {
		this.allInitOrdersRcvdActualDate = allInitOrdersRcvdActualDate;
	}
	public String getAllInitOrdersRcvdImage() {
		return allInitOrdersRcvdImage;
	}
	public void setAllInitOrdersRcvdImage(String allInitOrdersRcvdImage) {
		this.allInitOrdersRcvdImage = allInitOrdersRcvdImage;
	}
	public Date getAllInitOrdersRcvdPlanDate() {
		return allInitOrdersRcvdPlanDate;
	}
	public void setAllInitOrdersRcvdPlanDate(Date allInitOrdersRcvdPlanDate) {
		this.allInitOrdersRcvdPlanDate = allInitOrdersRcvdPlanDate;
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
	public Date getInitialManufOrderShippedActualDate() {
		return initialManufOrderShippedActualDate;
	}
	public void setInitialManufOrderShippedActualDate(
			Date initialManufOrderShippedActualDate) {
		this.initialManufOrderShippedActualDate = initialManufOrderShippedActualDate;
	}
	public String getInitialManufOrderShippedImage() {
		return initialManufOrderShippedImage;
	}
	public void setInitialManufOrderShippedImage(
			String initialManufOrderShippedImage) {
		this.initialManufOrderShippedImage = initialManufOrderShippedImage;
	}
	public Date getInitialManufOrderShippedPlanDate() {
		return initialManufOrderShippedPlanDate;
	}
	public void setInitialManufOrderShippedPlanDate(
			Date initialManufOrderShippedPlanDate) {
		this.initialManufOrderShippedPlanDate = initialManufOrderShippedPlanDate;
	}
	public Date getInitialOrderAtManufacturerActualDate() {
		return initialOrderAtManufacturerActualDate;
	}
	public void setInitialOrderAtManufacturerActualDate(
			Date initialOrderAtManufacturerActualDate) {
		this.initialOrderAtManufacturerActualDate = initialOrderAtManufacturerActualDate;
	}
	public String getInitialOrderAtManufacturerImage() {
		return initialOrderAtManufacturerImage;
	}
	public void setInitialOrderAtManufacturerImage(
			String initialOrderAtManufacturerImage) {
		this.initialOrderAtManufacturerImage = initialOrderAtManufacturerImage;
	}
	public Date getInitialOrderAtManufacturerPlanDate() {
		return initialOrderAtManufacturerPlanDate;
	}
	public void setInitialOrderAtManufacturerPlanDate(
			Date initialOrderAtManufacturerPlanDate) {
		this.initialOrderAtManufacturerPlanDate = initialOrderAtManufacturerPlanDate;
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
	public Date getProdOnEOMActualDate() {
		return prodOnEOMActualDate;
	}
	public void setProdOnEOMActualDate(Date prodOnEOMActualDate) {
		this.prodOnEOMActualDate = prodOnEOMActualDate;
	}
	public String getProdOnEOMImage() {
		return prodOnEOMImage;
	}
	public void setProdOnEOMImage(String prodOnEOMImage) {
		this.prodOnEOMImage = prodOnEOMImage;
	}
	public Date getProdOnEOMPlanDate() {
		return prodOnEOMPlanDate;
	}
	public void setProdOnEOMPlanDate(Date prodOnEOMPlanDate) {
		this.prodOnEOMPlanDate = prodOnEOMPlanDate;
	}
	public Date getProdReadyForDigiDistbnActualDate() {
		return prodReadyForDigiDistbnActualDate;
	}
	public void setProdReadyForDigiDistbnActualDate(
			Date prodReadyForDigiDistbnActualDate) {
		this.prodReadyForDigiDistbnActualDate = prodReadyForDigiDistbnActualDate;
	}
	public String getProdReadyForDigiDistbnImage() {
		return prodReadyForDigiDistbnImage;
	}
	public void setProdReadyForDigiDistbnImage(String prodReadyForDigiDistbnImage) {
		this.prodReadyForDigiDistbnImage = prodReadyForDigiDistbnImage;
	}
	public Date getProdReadyForDigiDistbnPlanDate() {
		return prodReadyForDigiDistbnPlanDate;
	}
	public void setProdReadyForDigiDistbnPlanDate(
			Date prodReadyForDigiDistbnPlanDate) {
		this.prodReadyForDigiDistbnPlanDate = prodReadyForDigiDistbnPlanDate;
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
	public Date getSuccessfulAOMARegistrationActualDate() {
		return successfulAOMARegistrationActualDate;
	}
	public void setSuccessfulAOMARegistrationActualDate(
			Date successfulAOMARegistrationActualDate) {
		this.successfulAOMARegistrationActualDate = successfulAOMARegistrationActualDate;
	}
	public String getSuccessfulAOMARegistrationImage() {
		return successfulAOMARegistrationImage;
	}
	public void setSuccessfulAOMARegistrationImage(
			String successfulAOMARegistrationImage) {
		this.successfulAOMARegistrationImage = successfulAOMARegistrationImage;
	}
	public Date getSuccessfulAOMARegistrationPlanDate() {
		return successfulAOMARegistrationPlanDate;
	}
	public void setSuccessfulAOMARegistrationPlanDate(
			Date successfulAOMARegistrationPlanDate) {
		this.successfulAOMARegistrationPlanDate = successfulAOMARegistrationPlanDate;
	}
	public String getOrdersOverallFlag() {
		return ordersOverallFlag;
	}
	public void setOrdersOverallFlag(String ordersOverallFlag) {
		this.ordersOverallFlag = ordersOverallFlag;
	}
	public String getPartsOverallFlag() {
		return partsOverallFlag;
	}
	public void setPartsOverallFlag(String partsOverallFlag) {
		this.partsOverallFlag = partsOverallFlag;
	}
	public String getScheduleOverallFlag() {
		return scheduleOverallFlag;
	}
	public void setScheduleOverallFlag(String scheduleOverallFlag) {
		this.scheduleOverallFlag = scheduleOverallFlag;
	}
	public String getUnattachedReportFlag() {
		return unattachedReportFlag;
	}
	public void setUnattachedReportFlag(String unattachedReportFlag) {
		this.unattachedReportFlag = unattachedReportFlag;
	}
	public String getPreparationOverallFlag() {
		return preparationOverallFlag;
	}
	public void setPreparationOverallFlag(String preparationOverallFlag) {
		this.preparationOverallFlag = preparationOverallFlag;
	}
	public String getDigitalRightsOverallFlag() {
		return digitalRightsOverallFlag;
	}
	public void setDigitalRightsOverallFlag(String digitalRightsOverallFlag) {
		this.digitalRightsOverallFlag = digitalRightsOverallFlag;
	}
	public String getDigitalSchedulingOverallFlag() {
		return digitalSchedulingOverallFlag;
	}
	public void setDigitalSchedulingOverallFlag(String digitalSchedulingOverallFlag) {
		this.digitalSchedulingOverallFlag = digitalSchedulingOverallFlag;
	}
	public String getLabelCopyOverallFlag() {
		return labelCopyOverallFlag;
	}
	public void setLabelCopyOverallFlag(String labelCopyOverallFlag) {
		this.labelCopyOverallFlag = labelCopyOverallFlag;
	}
	
	
}
