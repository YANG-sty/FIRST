package com.gree.first.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gree.first.annotation.RoutingDataSource;
import com.gree.first.contants.DataSourcess;
import com.gree.first.user.dao.UserSecendMapper;
import com.gree.first.user.domain.UserSecend;
import com.gree.first.user.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author yangLongFei 2020-11-22-10:14
 */
@Service
@Component
public class UserSecendServiceImpl extends ServiceImpl<UserSecendMapper, UserSecend> implements UserSecendService {

    /**
     * 从另外一个库(数据源)里面查询数据
     */
    @Override
    @RoutingDataSource(DataSourcess.SECEND_DB)
    public UserDto queryByName(String name) {
        //
        EntityWrapper<UserSecend> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", name);
        UserSecend user = this.selectOne(entityWrapper);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}