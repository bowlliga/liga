package com.csslect.app.command;
import org.springframework.ui.Model;
import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.MemberDTO;

public class AJoinCommand implements ACommand{

	@Override
	public void execute(Model model) {		
		String id = (String)model.asMap().get("id");
		String passwd = (String)model.asMap().get("passwd");	
		String name = (String)model.asMap().get("name");
		String phonenumber = (String)model.asMap().get("phonenumber");
		String birth = (String)model.asMap().get("birth");
		String email = (String)model.asMap().get("email");
		
		ANDao adao = new ANDao();
		int state = adao.anJoin(id, passwd, name, phonenumber, birth, email);
		
		model.addAttribute("anJoin", String.valueOf(state)); 
		
	}
	

	
}
