package com.ldg.service;

import com.ldg.pojo.Dynamic;
import com.ldg.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */

public interface DynamicService {

    boolean insertDynamic(Dynamic dynamic);

    boolean deleteDynamic(Long id);

    void updateFabulous(Long id);

    List<Dynamic> queryDynamicByTitle(String dynamics);

    List<Dynamic> queryDynamicByType(int type);

    List<Dynamic> queryDynamicAll();

    List<Dynamic> queryDynamicByTitleAndType(String name,int type);

    List<Dynamic> queryDynamicByDate(Date date);

    List<Dynamic> queryUserDynamic(Long useId);

    List<Dynamic> queryDynamicPage(int page,int size);

}
