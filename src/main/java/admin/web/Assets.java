package admin.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import admin.domain.User;

@Controller
@RequestMapping("/fakeAssets")
public class Assets {
	@RequestMapping(value="/css/bootstrap.min.css",method=RequestMethod.GET)
	public String bootstrap(){
		return "/css/bootstrap.min.css";
	}
	@RequestMapping(value="/css/ace-fonts.min.css",method=RequestMethod.GET)
	public String front(){
		return "/css/ace-fonts.min.css";
	}
	@RequestMapping(value="/css/font-awesome.min.css",method=RequestMethod.GET)
	public String awesome(){
		return "/css/font-awesome.min.css";
	}

	@RequestMapping(value="/css/ace.min.css",method=RequestMethod.GET)
	public String ace(){
		return "/css/ace.min.css";
	}
	@RequestMapping(value="/easyui/themes/bootstrap/easyui.css",method=RequestMethod.GET)
	public String easy(){
		return "/easyui/themes/bootstrap/easyui.css";
	}
	@RequestMapping(value="css/gui.css",method=RequestMethod.GET)
	public String gui(){
		return "css/gui.css";
	}

	
	@RequestMapping(value="{realWant}",method=RequestMethod.GET)
	public String toRealFile(@PathVariable String realWant){
		return realWant;
	}
}


