package com.sbm.artemis.demo.controller;

import com.sbm.artemis.demo.jms.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sbm.artemis.demo.jms.ArtemisProducer;

@RestController
public class RestApiController {
	
	@Autowired
	ArtemisProducer producer;
	
	@RequestMapping(value="/produce")
	public String produce(@RequestParam("msg")String msg){
		producer.send(msg);
		return "Done :: "+msg;
	}
	@RequestMapping(value="/produce",method = RequestMethod.POST)
	public String produce(@RequestBody  Person p){
		producer.send(p);
		return "Send Person Done :: " + p.toString();
	}
}