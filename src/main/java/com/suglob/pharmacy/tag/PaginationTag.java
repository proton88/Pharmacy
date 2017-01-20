package com.suglob.pharmacy.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(PaginationTag.class);
    private String locale;
    private String urlParamsPagination;
    private int page;
    private int countPages;
    public void setPage(Integer page) {
        this.page = page;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setUrlParamsPagination(String urlParamsPagination) {
        this.urlParamsPagination = urlParamsPagination;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    @Override
    public int doStartTag() throws JspException {
        String next;
        String previous;
        if (locale.equals("en")){
            next="Next";
            previous="Previous";
        }else {
            next="Вперед";
            previous="Назад";
        }
        try{
            if (countPages!=1) {
                JspWriter out = pageContext.getOut();
                if (page!=1){
                    out.write("<td><a href=\"main?page="+(page - 1)+"&"+urlParamsPagination+"\">"+previous+" </a></td>");
                }
                for (int i=1; i<=countPages; i++){
                    if (page==i){
                        out.write(""+i);
                    }else{
                        out.write("<a href=\"main?page="+i+"&"+urlParamsPagination+"\">"+i+"</a>");
                    }
                }
                if (page<countPages){
                    out.write("<td><a href=\"main?page="+(page + 1)+"&"+urlParamsPagination+"\"> "+next+"</a></td>");
                }
            }
        }catch (IOException e){
            LOGGER.log(Level.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
