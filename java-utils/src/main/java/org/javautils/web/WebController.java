package org.javautils.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController extends AbstractWebController {

	private final Map<String, PollResult> pollResults = new HashMap<String, PollResult>();
	
	@ModelAttribute("idTypes")
	public String[] types() {
		return new String[] {};
	}
	
	@ModelAttribute("form1")
	public Form1 form1() {
		return new Form1();
	}
	
	@ModelAttribute("form2")
	public Form2 form2() {
		return new Form2();
	}
	
	@Autowired
	@Resource(name="queueManagers")
	private Map<String, QueueManager> queueManagers = new HashMap<String, QueueManager>();
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/actions/action1.do")
	public String action1(Model model) {
		model.addAttribute("queueManagers", queueManagers.values());
		return "queues";
	}
	
	@PostConstruct
	public void init() {
		
		for(QueueManager queueManager : queueManagers.values()) {
			pollResults.put(queueManager.getKey(), new PollResult(queueManager.getEnv(), queueManager.getRegion()));
			new Timer("QueueManager poller" + queueManager.getEnv(), true).schedule(new TimerTask() {
				
				@Override
				public void run() {
					pollMqForDepth(queueManager);
				}
			}, 0, 3000);
		}
	}
	
	private void pollMqForDepth(QueueManager queueManager) {
		
	}
	
	@RequestMapping(value="/poll", method = RequestMethod.GET)
	public @ResponseBody
	Collection<PollResult> poll() {
		return pollResults.values();
	}

	@RequestMapping(value="/action1", method = RequestMethod.GET)
	public String action1(@ModelAttribute("form1") @Valid Form1 form1, BindingResult result, Model model) {
		
		if(result.hasErrors())
			return "query";
		
		// process
		model.addAttribute("results", "");
		// return to results.jsp
		return "results";
	}
	
	@RequestMapping(value="/action2", method = RequestMethod.GET)
	public String action1(@ModelAttribute("form2") @Valid Form2 form2, BindingResult result, Model model) {
		
		if(result.hasErrors())
			return "query";
		
		// process
		model.addAttribute("results", "");
		// return to results.jsp
		return "results";
	}
	
	public static class PollResult {
		private List<String> depths = new ArrayList<String>();
		private String updated;
		private final String env;
		private final String region;
		public PollResult(String env, String region) {
			super();
			this.env = env;
			this.region = region;
		}
		public List<String> getDepths() {
			return depths;
		}
		public void setDepths(List<String> depths) {
			this.depths = depths;
		}
		public String getUpdated() {
			return updated;
		}
		public void setUpdated(String updated) {
			this.updated = updated;
		}
		public String getEnv() {
			return env;
		}
		public String getRegion() {
			return region;
		}
		
	}
}
