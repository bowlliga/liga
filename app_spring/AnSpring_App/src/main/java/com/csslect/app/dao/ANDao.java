package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.dto.ANDto;
import com.csslect.app.dto.MemberDTO;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/dteam");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}
	
    public MemberDTO anLogin(String idin, String passwdin) {

    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from tbl_mb" 
							+ " where mb_id = '" + idin + "' and mb_passwd = '" + passwdin + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("mb_id");
				String name = resultSet.getString("mb_name");
				String phonenumber = resultSet.getString("mb_phonenum");
				String birth = resultSet.getString("mb_birth"); 
				String email = resultSet.getString("mb_email"); 

				adto = new MemberDTO(id, name, phonenumber, birth, email);							
			}	
			
			System.out.println("MemberDTO id : " + adto.getId());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
    
    public int anJoin(String id, String passwd, String name, 
    							String phonenumber, String birth, String email) { 
    	
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;

		try {
			connection = dataSource.getConnection();
			String query = "insert into tbl_mb(mb_id, mb_passwd, mb_name, mb_phonenum, mb_birth, mb_email) " + 
			               "values('" + id + "', '" + passwd + "', '" + name + "', '" + 
					        			phonenumber + "', '" + birth + "', '" + email  +"' )";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "가입성공!");
			} else {
				System.out.println(state + "가입실패!");
			}
			
		} catch (Exception e) {			
			System.out.println(e.getMessage());
		} finally {
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}

	public ArrayList<ANDto> anSelectMulti() {		
		
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select id, name, hire_date, image_path "					
							+ " from android" 
							+ " order by id desc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Date date = resultSet.getDate("hire_date"); 
				String imagePath = resultSet.getString("image_path"); 

				ANDto adto = new ANDto(id, name, date, imagePath);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos�겕湲�" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
	
	public int anInsertMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into android(id, name, hire_date, image_path) " + "values(" + id + ",'" 
							+ name + "'," + "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "�궫�엯�꽦怨�");				
			} else {
				System.out.println(state + "�궫�엯�떎�뙣");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	

	public int anUpdateMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// �븘�씠�뵒�뒗 �닔�젙�븷�닔 �뾾�쓬			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image_path = '" + dbImgPath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("�닔�젙1�꽦怨�");
				
			} else {
				System.out.println("�닔�젙1�떎�뙣");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// �븘�씠�뵒�뒗 �닔�젙�븷�닔 �뾾�쓬			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("�닔�젙2�꽦怨�");
				
			} else {
				System.out.println("�닔�젙2�떎�뙣");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from android where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("�궘�젣�꽦怨�");				
			} else {
				System.out.println("�궘�젣�떎�뙣");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}

		
	
	
}
