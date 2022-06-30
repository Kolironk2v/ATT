/////////////////////////////////////////////////////////////////////////
// Project Web Services
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.k2_ws.EthernetserviceDetails;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.user.WebServiceUserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.k2_ws.*;
import com.k2view.fabric.api.endpoint.Endpoint.*;
import com.k2view.graphIt.pool.GraphitPool;
import com.k2view.graphIt.serialize.Serializer;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.API_CALL_FNS.SharedLogic.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.common.SharedGlobals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends WebServiceUserCode {




	@webService(path = "", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = true, isCustomPayload = false, produce = {Produce.XML}, elevatedPermission = true)
	public static Object K2_GETCQ_SRV_ADDR_DME2(String i_requestername, String i_requestercomponentname, String i_requestermethodname, String i_requesterclienthostname, String i_middletierhostname, String i_requesterapplicationname, String i_conversation_id, String i_unique_transaction_id, String i_csi_qual_id, String i_csi_qual_address) throws Exception {
		/***********************************************************************************************************
		 K2_GETCQ_SRV_ADDR_DME2
		 -------------------
		 Author	  : Pallabi Meher
		 Description :
		 ****************************Prologue***********************************************************************
		 05/10/2017	R1712 	pm960y		Created new 	K2_GETCQ_SRV_ADDR_DME2 webservice.
		 02/04/2019 		ss747m		Replaced DBTemplate with StringBuilder
		 ***********************************************************************************************************/
		
		/*
		
		REVISED BY: Kaleb W
		05/05/2020			- Replaced StringBuilder response by Graphit
							- Changed data source to fabric
							- Removed unused variables
							- Removed redundant conditional statements
							- All sql queries excluded form java code and done in graphit
		
		06/08/2020			- Used StringBuilder in place of Concat operator for Additional Info
							- Moved creation of v_template_inputs down to after initial checks
		
		*/
		
		String v_requestTimeStamp = k2_currentTimeStampInFormat(FABRIC_TIME_FORMAT);
		StringBuilder sb = new StringBuilder();
		String v_severity = "";
		String v_methodName = "K2_GETCQ_SRV_ADDR_DME2";
		String v_programowner = "UPRSOR";
		String v_lineNumber = "";
		String v_errorId = "";
		Object v_cqResponse = new Object();
		String v_action = "SRCH";
		String p_xsd_url = "http://ccrr.att.com/Getcq_srv_addr_DME2Response.xsd";
		String p_ws_name = "Getcq_srv_addr_DME2Response";
		String p_url = "http://xmlns.oracle.com/xdb";
		String p_response = "Response";
		String o_xml = "";
		Object[] v_exception = null;
		Object[] v_errmsg = null;
		
		
		//input validations
		fnValidateOracleInput("CSIDATA.CQ_SRV_ADDR.CSI_QUAL_ID", i_csi_qual_id);
		fnValidateOracleInput("CSIDATA.CQ_SRV_ADDR.CSI_QUAL_ADDRESS", i_csi_qual_address);
		
		try {
		
		    // CHECK IF ONE REQUIRED INPUT IS PROVIDED
		    if(k2_isNullorEmptyString(i_csi_qual_id) && k2_isNullorEmptyString(i_csi_qual_address)){
		        v_errorId = "132";
		        v_errmsg = new Object[]{"Input Parameters are required"};
		        v_severity = "CRITICAL";
			
			
			
			o_xml = "" + raiseK2_GetGenericWsError(v_errorId, p_ws_name, p_xsd_url, "http://xmlns.oracle.com/xdb",  v_errmsg);
			
			
		    	return o_xml;
			}
		
		    // CHECK IF MORE THAN ONE REQUIRED INPUTS ARE PROVIDED
		    if(!k2_isNullorEmptyString(i_csi_qual_id) && !k2_isNullorEmptyString(i_csi_qual_address)){
		        v_errorId = "76";
		        v_errmsg = new Object[]{"Both CSI_QUAL_ID and CSI_QUAL_ADDRESS passed as input"};
		        v_severity = "CRITICAL";
				
		     	o_xml = "" + raiseK2_GetGenericWsError(v_errorId, p_ws_name, p_xsd_url, "http://xmlns.oracle.com/xdb",  v_errmsg);   
			
		    	return o_xml;
			}
		
		    //ADD GRAPHIT INPUTS
			Map<String, Object> v_template_inputs = new HashMap<>();
		    v_template_inputs.put("p_ws_name", p_ws_name);
		    v_template_inputs.put("p_xsd_url", p_xsd_url);
		    v_template_inputs.put("p_url", p_url);
		
		    if(!k2_isNullorEmptyString(i_csi_qual_id)) {
		    	v_template_inputs.put("i_csi_qual_id", i_csi_qual_id);
		    	v_template_inputs.put("i_csi_qual_address", null);
			} else {
				v_template_inputs.put("i_csi_qual_address", i_csi_qual_address);
				v_template_inputs.put("i_csi_qual_id", null);
		    }
			String accept = request().getHeader("Accept");
			if(accept.contains("*"))
				accept = "application/xml";
			final Serializer.Type type = UserCodeDelegate.graphitFormat(accept);
			final StringWriter writer = new StringWriter();
			
			GraphitPool.Entry entry = getLuType().graphitPool().get("K2_GETCQ_SRV_ADDR_DME2.graphit");
			entry.get().run(v_template_inputs, type, writer);
			v_cqResponse = writer.toString();
		
			return v_cqResponse;
			//return o_xml = "" + graphit("K2_GETCQ_SRV_ADDR_DME2.graphit", v_template_inputs);
		}
		
		catch (Exception ex) {
		
		    v_errorId = "9995";
		    v_lineNumber = fnGetStackElements(ex, "LineNumber");
		    v_methodName = fnGetStackElements(ex, "FileName");
		    v_exception = new Object[]{ex.getMessage()};
			o_xml = "" + raiseK2_GetGenericWsError(v_errorId, p_ws_name, p_xsd_url, "http://xmlns.oracle.com/xdb",  v_exception ); //added 01/312022
			return o_xml;
		
		} finally {
		
		    String v_otherinfo = "" + sb.append("i_csi_qual_id = ").append(i_csi_qual_id).append("&i_csi_qual_address = ").append(i_csi_qual_address);
		    String additional_info = "Response Size: " + o_xml.length();
		    //fnInsertStatistics(i_requestername, i_requestercomponentname, i_requestermethodname, i_requesterclienthostname, i_middletierhostname, i_requesterapplicationname, "CSIDATA.PKG_CSI_DATA_SERVICE", "getcq_srv_addr_dme2", v_requestTimeStamp, k2_currentTimeStampInFormat(FABRIC_TIME_FORMAT), v_action, v_otherinfo, i_conversation_id, i_unique_transaction_id, v_errorId, v_errmsg, v_severity, v_exception, additional_info, v_lineNumber, v_methodName);
			fnInsertStatistics(i_requestername, i_requestercomponentname, i_requestermethodname, i_requesterclienthostname, i_middletierhostname, i_requesterapplicationname, "CSIDATA.PKG_CSI_DATA_SERVICE", "getcq_srv_addr_dme2", v_requestTimeStamp, k2_currentTimeStampInFormat(FABRIC_TIME_FORMAT), v_action, v_otherinfo, i_conversation_id, i_unique_transaction_id, v_errorId, v_errmsg, v_severity, v_exception, additional_info, v_lineNumber, v_methodName, v_programowner);
		
		}
	}


	@webService(path = "", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = true, isCustomPayload = false, produce = {Produce.XML}, elevatedPermission = false)
	public static Object GetSpeedInfo_DME2(String i_requestername, String i_requestercomponentname, String i_requestermethodname, String i_requesterclienthostname, String i_requesterapplicationname, String i_unique_transaction_id, String i_conversation_id, String i_srvabbr, String i_dwspeed, String i_upspeed) throws Exception {
		/******************************************************************************************************
		GetSpeedInfo_DME2
		---------------------------------
		Author	  : Liron Kolet ()
		Description : To select data from sapmpsor_tenant_account table
		
		****************************Prologue****************************************************************
		24/06/2022		 	''		Created new GetSpeedInfo_DME2 webservice.
		***********************************************************************************************************/
			
		/***********************/
		/* DECLARING VARIABLES */
		/***********************/		
		String vRequestTimestamp = k2_currentTimeStampInFormat(FABRIC_TIME_FORMAT);
		Object vTwinningResponse = "";
		String l_where_str1 = "";
		String vAdditionalInfo="";
		Object result = "";
		
		String vComponentName = "CSIDATA.PKG_CSI_DATA_SERVICE";
		String vProgramName = "PKG_CSI_DATA_SERVICE";
		String vProgramOwner = "CSIDATA";
		String vMethodName = "GetSpeedInfo_DME2";
		String vRespInfo = "GetSpeedInfoDME2Response";
		String vLineNumber = "";
		String vErrorId = "";
		String vAction = "SRCH";
		Object[] vErrMsg = null;
		String vSeverity = "";
		Object[] vException = null;
		
		String fabricErrorID = "";
		Db fabric = fabric();
		boolean rollBack = false;
		String vOtherinfo = "InputParm: " + i_srvabbr + "," + i_dwspeed + "," + i_upspeed;
		Map<String, Object> cond = new HashMap<>();
		String pXsdUrl = "http://ccrr.att.com/getspeedinfo_dme2.xsd";
		String pUrl = "http://xmlns.oracle.com/xdb";
		String xForm = "xdb";
		String proc = "Proc";
		String vInput = "";
		StringBuilder vGroupType = new StringBuilder();
		StringBuilder conditions  = new StringBuilder();
		
		try {
			/***********************/
			/* CHECK INPUT PARAMS  */
			/***********************/
			Boolean eInputParametersMissingSrvAbbr = k2_isNullorEmptyString(i_srvabbr);
			Boolean eInputParametersMissingDwSpeed = k2_isNullorEmptyString(i_dwspeed);
			Boolean eInputParametersMissingUpSpeed = k2_isNullorEmptyString(i_upspeed);

			if (k2_isNullorEmptyString(i_srvabbr)) {
				fabricErrorID = "125";
				vSeverity = "CRITICAL";
				vLineNumber = "231";
				vTwinningResponse = raise_GetGenericWsErrorV2(fabricErrorID, vRespInfo, pXsdUrl, pUrl, vErrMsg, proc, xForm);
				return vTwinningResponse;
			}

			/***********************/
			/*   GENERATE QUERY    */
			/***********************/
			if (!k2_isNullorEmptyString(i_dwspeed)) {
				conditions.append(" AND s.dw_speed = " + i_dwspeed);
			}

			if (!k2_isNullorEmptyString(i_upspeed)) {
				conditions.append(" AND s.up_speed = " + i_upspeed);
			}

			/***********************/
			/*     FETCH DATA      */
			/***********************/
			log.info(conditions.toString());
			Map<String, Object> graphitParams = new HashMap<>();
			graphitParams.put("conditions",conditions.toString());

			String accept = request().getHeader("Accept");
			if(accept.contains("*"))
				accept = "application/xml";
			final Serializer.Type type = UserCodeDelegate.graphitFormat(accept);
			final StringWriter writer = new StringWriter();
			GraphitPool.Entry entry = getLuType().graphitPool().get("Get_Speed_Info_DME2.graphit");
			entry.get().run(graphitParams, type, writer);
			result = writer.toString();

			/***********************/
			/*CHECK EMPTY DATA SET */
			/***********************/
			if (!result.toString().contains("dwSpeed")) {
				vErrorId = "20001";
				result =  raise_GetGenericWsError(vErrorId, "GetSpeedInfoDME2Response", "http://ccrr.att.com/getSvcAccountEventDME2Response.xsd", "http://xmlns.oracle.com/xdb", null, "Proc");
			}

			return result;
		}
		catch (Exception ex) {
			/***********************/
			/*  OTHER EXCEPTIONS   */
			/***********************/
			vException = new Object[]{ex.getMessage()};
			vLineNumber = fnGetStackElements(ex, "LineNumber");
			vProgramName = fnGetStackElements(ex, "FileName");
			vErrorId = "9995";
			result = raise_GetGenericWsError(vErrorId, "getSvcAccountEventDME2Response", "http://ccrr.att.com/getSvcAccountEventDME2Response.xsd", "http://xmlns.oracle.com/xdb", vException, "Proc");
			return result;
		}
		finally {
			/***********************/
			/*  INSERT STATISTICS  */
			/***********************/
			vAdditionalInfo = "InputParm: " + i_srvabbr + "," + i_dwspeed + "," + i_upspeed;
			vOtherinfo = "Response Size:" + result.toString().length();
			fnInsertStatistics(i_requestername, i_requestercomponentname, i_requestermethodname, i_requesterclienthostname, "CSIDATA", i_requesterapplicationname, vComponentName, vMethodName, vRequestTimestamp, k2_currentTimeStampInFormat(FABRIC_TIME_FORMAT), vAction, vOtherinfo, i_conversation_id, i_unique_transaction_id, vErrorId, vException, vSeverity, vException+"", vAdditionalInfo, vLineNumber, vProgramName, vProgramOwner);
		}
	}
		
		
}
