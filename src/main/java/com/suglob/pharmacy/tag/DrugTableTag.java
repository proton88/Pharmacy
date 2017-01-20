package com.suglob.pharmacy.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class DrugTableTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(DrugTableTag.class);
    private String locale;
    private int rows;
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        String caption;
        if (locale.equals("en")){
            caption="Drugs";
        }else {
            caption="Препараты";
        }
        try{
            JspWriter out = pageContext.getOut();
            out.write("<table class=\"recipe\">");
            out.write("<tr><th>"+caption+"</th></tr>");
            out.write("<tr><td>");
        }catch (IOException e){
            LOGGER.log(Level.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (rows-- > 1) {
            try {
                pageContext.getOut().write("</td></tr><tr><td>");
            } catch (IOException e) {
                throw new JspTagException(e.getMessage());
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</td></tr></table>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
