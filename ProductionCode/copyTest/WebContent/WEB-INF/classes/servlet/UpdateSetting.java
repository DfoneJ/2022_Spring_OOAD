package servlet;

import java.io.IOException;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;

import WebGUI.SettingInformation;

/**
 * Servlet implementation class UpdateSetting
 */
public class UpdateSetting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSetting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SettingInformation settingInformation = new SettingInformation();
		settingInformation.setPath(request.getParameter("path"));
		settingInformation.setStructureAnalyzer(Boolean.parseBoolean(request.getParameter("StructureAnalyzer")));
		settingInformation.setVariableAnalyzer(Boolean.parseBoolean(request.getParameter("VariableAnalyzer")));
		settingInformation.setTextAnalyzer(Boolean.parseBoolean(request.getParameter("TextAnalyzer")));

		settingInformation.setThresholdOfText(Double.parseDouble(request.getParameter("ThresholdOfText")));
		settingInformation.setThresholdOfStructure(Double.parseDouble(request.getParameter("ThresholdOfStructure")));
		settingInformation.setThresholdOfVariable(Double.parseDouble(request.getParameter("ThresholdOfVariable")));
		settingInformation.setThresholdOfTotal(Double.parseDouble(request.getParameter("ThresholdOfTotal")));

		settingInformation.setWeightOfText(Integer.parseInt(request.getParameter("WeightOfText")));
		settingInformation.setWeightOfStructure(Integer.parseInt(request.getParameter("WeightOfStructure")));
		settingInformation.setWeightOfVariable(Integer.parseInt(request.getParameter("WeightOfVariable")));
		
		settingInformation.createSettingRecord();
	}

}
