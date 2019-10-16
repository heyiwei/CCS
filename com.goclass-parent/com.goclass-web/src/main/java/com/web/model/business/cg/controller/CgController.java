package com.web.model.business.cg.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;

import javax.tools.Tool;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.web.model.business.cg.bean.create.CreateTaskBean;
import com.web.model.business.cg.tool.transform.CreateTaskTransform;
import com.web.model.rpc.client.container.ResultOfClassStrategyCreateTask;
import com.web.model.rpc.client.container.ResultOfClassStrategyGetTasksStatus;
import com.web.model.rpc.client.container.ResultOfClassStrategyRunTask;
//import com.web.model.rpc.client.container.ResultOfGetClassStrategyRule;
import com.web.model.rpc.server.call.CallingTool;
import com.web.model.rpc.server.call.PropertyCopyTool;
import com.web.model.rpc.server.source.ClassStrategyRule;
//import com.web.model.rpc.server.source.ResultOfClassStrategyCreateTask;
import com.web.model.rpc.server.source.ResultOfGetClassStrategyRule;
import com.web.model.rpc.server.source.ResultOfClassStrategyDelTask;
import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTasksStatus;
//import com.web.model.rpc.server.source.ResultOfClassStrategyRunTask;
//import com.web.model.rpc.server.source.ResultOfGetClassStrategyRule;

@RestController

public class CgController {
	CallingTool globalCallingTool = new CallingTool();
	
	//创建分班任务
    @CrossOrigin
	@RequestMapping("/class/grouping/createtask")
	public ResultOfClassStrategyCreateTask createTask(
			@RequestBody CreateTaskBean createTaskData) {
		CreateTaskTransform tools = new CreateTaskTransform();
		ClassStrategyRule classStrategyRule = new ClassStrategyRule();
		//封装参数
		classStrategyRule.setSubjectTeacherNumber(tools.subjectTeacherNumberTransform(
				createTaskData.getSubjectTeacherNumber()));
		classStrategyRule.setSectionStudentNumber(tools.sectionStudentNumberTransform(
				createTaskData.getSectionStudentNumber()));
		classStrategyRule.setMaxAndMinClassStudentNumber(createTaskData.getMaxAndMinClassStudentNumber());
		classStrategyRule.setRuning_time(createTaskData.getRuningTime());
		//远程调用
		ResultOfClassStrategyCreateTask returnMessage = new ResultOfClassStrategyCreateTask();
		try {
			PropertyCopyTool.Copy(globalCallingTool.createTaskForClassStrategy(classStrategyRule), returnMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//运行分班任务
    @CrossOrigin
	@RequestMapping("/class/grouping/runtask")
	public ResultOfClassStrategyRunTask runTask(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage){
    	ResultOfClassStrategyRunTask returnMessage = new ResultOfClassStrategyRunTask();
    	try {
			PropertyCopyTool.Copy(globalCallingTool.runTaskForClassStrategy(taskId, stage), returnMessage);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//获取现有任务及其运行情况
    @CrossOrigin
	@RequestMapping("/class/grouping/taskstatus")
	public ResultOfClassStrategyGetTasksStatus getTasksStatusForClassStrategy() {
    	ResultOfClassStrategyGetTasksStatus returnMessage = new ResultOfClassStrategyGetTasksStatus();
    	try {
			PropertyCopyTool.Copy(globalCallingTool.getTasksStatusForClassStrategy(), returnMessage);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//获取任务结果
    @CrossOrigin
	@RequestMapping("/class/grouping/result")
	public ResultOfClassStrategyGetTaskResult getTaskResultForClassStrategy(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage
			) {
//    	ResultOfClassStrategyGetTaskResult returnMessage = new ResultOfClassStrategyGetTaskResult();
		return globalCallingTool.getTaskResultForClassStrategy(taskId, stage);
	}
	
	
	//获取分班任务的现存规则
    @CrossOrigin
	@RequestMapping("/class/grouping/rule")
	public ResultOfGetClassStrategyRule getClassStrategyRule(
			@RequestParam(value = "taskId") int taskId) {
//    	ResultOfGetClassStrategyRule returnMessage = new ResultOfGetClassStrategyRule();
//    	try {
////    		ResultOfGetClassStrategyRule resultOfGetClassStrategyRule = globalCallingTool.getClassStrategyRule(taskId);
//			PropertyCopyTool.Copy(globalCallingTool.getClassStrategyRule(taskId), returnMessage);
//			PropertyCopyTool.Copy(globalCallingTool.getClassStrategyRule(taskId).getRule(), 
//					returnMessage.getRule());
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("复制失败");
//			e.printStackTrace();
//		}
		return globalCallingTool.getClassStrategyRule(taskId);
	}
	
	
	//删除分班任务
    @CrossOrigin
	@RequestMapping("/class/grouping/delete")
	public ResultOfClassStrategyDelTask delTaskForClassStrategy(
			@RequestParam(value = "taskId") int taskId
			) {
		return globalCallingTool.delTaskForClassStrategy(taskId);
	}
}
