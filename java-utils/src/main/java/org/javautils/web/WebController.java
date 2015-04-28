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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController extends AbstractWebController {

	private final Map<String, PollResult> pollResults = new HashMap<String, PollResult>();
	
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
	
	@RequestMapping(value="/poll", method = RequestMethod.GET)
	public @ResponseBody
	Collection<PollResult> poll() {
		return pollResults.values();
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
