package com.tel.autosysframework.configureclasses;

import java.io.Serializable;

public class LTEParameters implements Serializable {
	
	/************** Default Parameter *******************/
	private String default_param = "";
	public void setDefault_param(String default_param) {
		this.default_param = default_param;
	}
	public String getDefault_param() {
		return default_param;
	}
	/************** General Parameters ******************/
	private String ChannelType = "";
	private String TransportBlock = "";
	private String Blocksize1 = "";
	private String Blocksize2 = "";
	private String inputpath1 = "";
	private String inputpath2 = "";
	private String outputpath = "";
	private String twoblocks = "";
	private String GenerateTestVector = "";
	private String Generalparam_written = "";
	private String Object_written = "";
	
	public void setChannelType(String channelType) {
		ChannelType = channelType;
	}
	public String getChannelType() {
		return ChannelType;
	}
	public void setTransportBlock(String transportBlock) {
		TransportBlock = transportBlock;
	}
	public String getTransportBlock() {
		return TransportBlock;
	}
	public void setBlocksize1(String blocksize1) {
		Blocksize1 = blocksize1;
	}
	public String getBlocksize1() {
		return Blocksize1;
	}
	public void setBlocksize2(String blocksize2) {
		Blocksize2 = blocksize2;
	}
	public String getBlocksize2() {
		return Blocksize2;
	}
	public void setInputpath1(String inputpath1) {
		this.inputpath1 = inputpath1;
	}
	public String getInputpath1() {
		return inputpath1;
	}
	public void setInputpath2(String inputpath2) {
		this.inputpath2 = inputpath2;
	}
	public String getInputpath2() {
		return inputpath2;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	public String getOutputpath() {
		return outputpath;
	}
	public void setTwoblocks(String twoblocks) {
		this.twoblocks = twoblocks;
	}
	public String getTwoblocks() {
		return twoblocks;
	}
	public void setGenerateTestVector(String generateTestVector) {
		GenerateTestVector = generateTestVector;
	}
	public String getGenerateTestVector() {
		return GenerateTestVector;
	}
	
	public void setGeneralparam_written(String generalparam_written) {
		Generalparam_written = generalparam_written;
	}
	public String getGeneralparam_written() {
		return Generalparam_written;
	}
	
	public void setObject_written(String object_written) {
		Object_written = object_written;
	}
	public String getObject_written() {
		return Object_written;
	}
	/****************************************************/
	
	/******** Resource Element Mapper Parameters ********/
	private String ConfigurationType = "";
	private String Bandwidth = "";
	private String REM_written = "";
	
	public void setConfigurationType(String configurationType) {
		ConfigurationType = configurationType;
	}
	public String getConfigurationType() {
		return ConfigurationType;
	}
	public void setBandwidth(String bandwidth) {
		Bandwidth = bandwidth;
	}
	public String getBandwidth() {
		return Bandwidth;
	}
	public void setREM_written(String rEM_written) {
		REM_written = rEM_written;
	}
	public String getREM_written() {
		return REM_written;
	}
	/****************************************************/

	/************** CPI Parameters **********************/
	private String CP_Type = "";
	private String CPI_Layer_nos = "";
	private String CPI_IFFT_Points = "";
	private String l = "";
	private String CPI_written = "";
	
	public void setCP_Type(String cP_Type) {
		CP_Type = cP_Type;
	}
	public String getCP_Type() {
		return CP_Type;
	}	

	public void setCPI_Layer_nos(String cPI_Layer_nos) {
		CPI_Layer_nos = cPI_Layer_nos;
	}
	public void setCPI_IFFT_Points(String cPI_IFFT_Points) {
		CPI_IFFT_Points = cPI_IFFT_Points;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getL() {
		return l;
	}
	public String getCPI_IFFT_Points() {
		return CPI_IFFT_Points;
	}
	public void setCPI_written(String cPI_written) {
		CPI_written = cPI_written;
	}
	public String getCPI_written() {
		return CPI_written;
	}
	public String getCPI_Layer_nos() {
		return CPI_Layer_nos;
	}	
	/****************************************************/

	/************** OFDM Parameters *********************/
	private String OFDM_Layer_nos = "";
	private String OFDM_IFFT_Points = "";
	private String OFDM_written = "";

	public void setOFDM_Layer_nos(String oFDM_Layer_nos) {
		OFDM_Layer_nos = oFDM_Layer_nos;
	}
	public String getOFDM_Layer_nos() {
		return OFDM_Layer_nos;
	}
	public void setOFDM_IFFT_Points(String oFDM_IFFT_Points) {
		OFDM_IFFT_Points = oFDM_IFFT_Points;
	}
	public String getOFDM_IFFT_Points() {
		return OFDM_IFFT_Points;
	}	
	public void setOFDM_written(String oFDM_written) {
		OFDM_written = oFDM_written;
	}
	public String getOFDM_written() {
		return OFDM_written;
	}
	/****************************************************/
	
	/************** Precoding Parameters ****************/
	private String Pr_NumberofLayers = "";
	private String Pr_AntennaPorts = "";
	private String Pr_CodeBookIndex = "";
	private String precoding_written = "";

	public void setPr_NumberofLayers(String pr_NumberofLayers) {
		Pr_NumberofLayers = pr_NumberofLayers;
	}
	public String getPr_NumberofLayers() {
		return Pr_NumberofLayers;
	}
	public void setPr_AntennaPorts(String pr_AntennaPorts) {
		Pr_AntennaPorts = pr_AntennaPorts;
	}
	public String getPr_AntennaPorts() {
		return Pr_AntennaPorts;
	}
	public void setPrecoding_written(String precoding_written) {
		this.precoding_written = precoding_written;
	}
	public String getPrecoding_written() {
		return precoding_written;
	}
	public void setPr_CodeBookIndex(String pr_CodeBookIndex) {
		Pr_CodeBookIndex = pr_CodeBookIndex;
	}
	public String getPr_CodeBookIndex() {
		return Pr_CodeBookIndex;
	}	
	/****************************************************/
	
	/************** Scrambler Parameters ****************/
	private String nRNTI = "";
	private String ns = "";
	private String N_cell_id = "";
	private String Scarmblerwritten = "";
	
	public void setnRNTI(String nRNTI) {
		this.nRNTI = nRNTI;
	}
	public String getnRNTI() {
		return nRNTI;
	}
	public void setNs(String ns) {
		this.ns = ns;
	}
	public String getNs() {
		return ns;
	}
	public void setScarmblerwritten(String scarmblerwritten) {
		Scarmblerwritten = scarmblerwritten;
	}
	public String getScarmblerwritten() {
		return Scarmblerwritten;
	}
	public void setN_cell_id(String n_cell_id) {
		N_cell_id = n_cell_id;
	}
	public String getN_cell_id() {
		return N_cell_id;
	}
	/****************************************************/
	
	/************** RateMatching Parameters *************/
	private String Nir = "";
	private String Qm = "";
	private String Nl = "";
	private String Rv_idx = "";
	private String RM_written = "";
	public void setQm(String qm) {
		Qm = qm;
	}
	public String getQm() {
		return Qm;
	}
	public void setNl(String nl) {
		Nl = nl;
	}
	public String getNl() {
		return Nl;
	}
	public void setRM_written(String rM_written) {
		RM_written = rM_written;
	}
	public String getRM_written() {
		return RM_written;
	}
	public void setRv_idx(String rv_idx) {
		Rv_idx = rv_idx;
	}
	public String getRv_idx() {
		return Rv_idx;
	}
	public void setNir(String nir) {
		Nir = nir;
	}
	public String getNir() {
		return Nir;
	}
	/****************************************************/

	/************ LayerMapper Parameters ****************/
	private String lm_NumberofLayers = "";
	private String lm_AntennaPorts = "";
	private String lm_CodeBookIndex = "";
	private String lm_written = "";

	public void setLm_NumberofLayers(String lm_NumberofLayers) {
		this.lm_NumberofLayers = lm_NumberofLayers;
	}
	public String getLm_NumberofLayers() {
		return lm_NumberofLayers;
	}
	public void setLm_AntennaPorts(String lm_AntennaPorts) {
		this.lm_AntennaPorts = lm_AntennaPorts;
	}
	public void setLm_written(String lm_written) {
		this.lm_written = lm_written;
	}
	public String getLm_written() {
		return lm_written;
	}
	public String getLm_AntennaPorts() {
		return lm_AntennaPorts;
	}
	public void setLm_CodeBookIndex(String lm_CodeBookIndex) {
		this.lm_CodeBookIndex = lm_CodeBookIndex;
	}
	public String getLm_CodeBookIndex() {
		return lm_CodeBookIndex;
	}
	/****************************************************/
	
	/************** Modulator Parameters ****************/
	private String Mod_type = "";
	private String Mod_written = "";

	public void setMod_type(String mod_type) {
		Mod_type = mod_type;
	}

	public String getMod_type() {
		return Mod_type;
	}
	
	public void setMod_written(String mod_written) {
		Mod_written = mod_written;
	}
	public String getMod_written() {
		return Mod_written;
	}
	/****************************************************/
}
