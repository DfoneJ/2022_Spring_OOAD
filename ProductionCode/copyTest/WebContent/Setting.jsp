<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" import="WebGUI.*"%>
<% SettingInformation settingInformation = new SettingInformation(); %>
			<form id="settingForm">
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    Normal
				  </div>
				  <div class="panel-body">
				  	<div class="input-group">
					  <span class="input-group-addon">path:</span>
					  <input type="text" class="form-control" name="path" value="<%=settingInformation.getPath()%>"></input>
					</div>
				  </div>
				</div>
				
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    Analyzer
				  </div>
				  <div class="panel-body">
				  	<div class="input-group">
					  <span class="input-group-addon">Structure:</span>
					  <input type="text" class="form-control" name="StructureAnalyzer" value="<%=settingInformation.isStructureAnalyzer()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Variable:</span>
					  <input type="text" class="form-control" name="VariableAnalyzer" value="<%=settingInformation.isVariableAnalyzer()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Text:</span>
					  <input type="text" class="form-control" name="TextAnalyzer" value="<%=settingInformation.isTextAnalyzer()%>"></input>
					</div>
				  </div>
				</div>
				
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    Threshold
				  </div>
				  <div class="panel-body">
				  	<div class="input-group">
					  <span class="input-group-addon">Text:</span>
					  <input type="text" class="form-control" name="ThresholdOfText" value="<%=settingInformation.getThresholdOfText()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Structure:</span>
					  <input type="text" class="form-control" name="ThresholdOfStructure" value="<%=settingInformation.getThresholdOfStructure()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Variable:</span>
					  <input type="text" class="form-control" name="ThresholdOfVariable" value="<%=settingInformation.getThresholdOfVariable()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Total:</span>
					  <input type="text" class="form-control" name="ThresholdOfTotal" value="<%=settingInformation.getThresholdOfTotal()%>"></input>
					</div>
				  </div>
				</div>
				
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    Weight
				  </div>
				  <div class="panel-body">
				  	<div class="input-group">
					  <span class="input-group-addon">Text:</span>
					  <input type="text" class="form-control" name="WeightOfText" value="<%=settingInformation.getWeightOfText()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Structure:</span>
					  <input type="text" class="form-control" name="WeightOfStructure" value="<%=settingInformation.getWeightOfStructure()%>"></input>
					</div>
					<div class="input-group">
					  <span class="input-group-addon">Variable:</span>
					  <input type="text" class="form-control" name="WeightOfVariable" value="<%=settingInformation.getWeightOfVariable()%>"></input>
					</div>
				  </div>
				</div>
			</form>	
<script>


</script>