package com.ser.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ser.component.Grid;
import com.ser.component.index;
import com.ser.index.Index;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * Servlet implementation class globalServelt
 */
@WebServlet("/globalServelt")
public class globalServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public globalServelt() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*request.setAttribute("index", idx);*/
		Index idx = new Index(this);
		idx.compile();
		try {
			idx.passeParametre(request);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		Grid g = new Grid(
				new String[][]{
						{"ID","color:red;"},
						{"nom","color:blue;"},
						{"ville"}
				},
				new HashMap<String, String[]>(){{
					put("1", new String[]{"1","David","Washington"});
					put("2", new String[]{"2","Pierre","Delhi"});
					put("3", new String[]{"3","Leo","Berlin"});
				}}
		);		
		request.setAttribute("gridHead", g.getParameters());
		request.setAttribute("gridData", g.getData());
	
		this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
