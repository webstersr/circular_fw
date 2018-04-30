package com.ser.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ser.index.Index;
import com.ser.servlets.globalServelt;

public class index {

	public static void main(String[] args) throws IOException {
		Index i = new Index();
		i.compile();
		System.out.println("ghghghghg");
	}

}
