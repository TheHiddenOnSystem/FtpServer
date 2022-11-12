package com.onsystem.ftpserver.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
@Scope(scopeName = "prototype")
public class ManagerAttributesSession {
    @Getter
    public enum AttributesType{
        ID("id");
        private final String attributeName;
        AttributesType(String attributeName) {
            this.attributeName=attributeName;
        }
    }
    @Autowired
    private HttpSession httpSession;


    public AttributeSession getAttributesInHttpSession(){
        AttributeSession attributeSession = new AttributeSession();
        httpSession.getAttributeNames().asIterator().forEachRemaining(a->{
            if(a == AttributesType.ID.getAttributeName()){
                attributeSession.setObjectId((ObjectId) this.httpSession.getAttribute(a));
            }
        });

        return attributeSession;
    }
    public void setAttributesInHttpSession(AttributeSession attributesInHttpSession){
        httpSession.setAttribute(AttributesType.ID.getAttributeName(), attributesInHttpSession.getObjectId());
    }
}