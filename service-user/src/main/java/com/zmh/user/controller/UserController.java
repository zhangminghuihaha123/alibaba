package com.zmh.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.zmh.user.entry.User;
import com.zmh.user.mapper.UserMapper;
import com.zmh.user.util.Result;
import com.zmh.user.util.TokenUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/resport")
    public Result UserResport(@RequestBody User user){
        try{
            redisTemplate.opsForValue().set("name:"+user.getUsername(),user,30,TimeUnit.MINUTES);
            String SQLMESSAGE="INSERT INTO user(username,password) VALUES ("+"'"+user.getUsername()+"'"+","+"'"+user.getPassword()+"'"+");";
            amqpTemplate.convertAndSend("/MYSQLMQ","INSERT",SQLMESSAGE);
            return Result.success();
        }catch (Exception e){
            return Result.error();
        }
    }

    @PostMapping("/login")
    public Result UserLogin(@RequestBody User user){
        try{
            if(redisTemplate.opsForValue().get("name:"+user.getUsername()) != null){
                if(((User) redisTemplate.opsForValue().get("name:"+user.getUsername())).getPassword().equals(user.getPassword())){
                    /*从数据库中拿取数据，因为Redis只存储了用户名和密码*/
                    QueryWrapper wrapper=new QueryWrapper();
                    wrapper.eq("username",user.getUsername());
                    User users=userMapper.selectOne(wrapper);
                    /*登录成功给予Token*/
                    users.setToken(TokenUtils.getToken(users.getUsername(),users.getPassword()));
                    return Result.success(users);
                }else{
                    return Result.error();
                }
            }else {
                /*有可能redis过时间，从数据库拿*/
                QueryWrapper wrapper=new QueryWrapper();
                wrapper.eq("username",user.getUsername());
                wrapper.eq("password",user.getPassword());
                User user1 = userMapper.selectOne(wrapper);
                if(user1 == null){
                    return Result.error();
                }
                /*登录成功给予Token*/
                user1.setToken(TokenUtils.getToken(user1.getUsername(),user1.getPassword()));
                return Result.success(user1);
            }
        }catch (Exception e){
            return Result.error();
        }
    }



    /*查询所有用户数据*/
    @GetMapping("/select")
    public Result UserList(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String username){
        HashMap map=new HashMap();
        HashMap SQLmap=new HashMap();
        try{
            PageHelper.startPage(pageNum,pageSize);
            SQLmap.put("username",username);
            List<User> users = userMapper.SelectQueryUser(SQLmap);
            map.put("data",users);
            Integer total = userMapper.getTotal(username);
            map.put("total",total);
            return Result.success(map);
        }catch (Exception e){
            return Result.error();
        }
    }


    @PostMapping("/save")
    public Result UserSave(@RequestBody User user){
        try{
            if(user.getId() != null){
                /*修改数据*/
                QueryWrapper wrapper=new QueryWrapper();
                wrapper.eq("id",user.getId());
                userMapper.update(user,wrapper);
            }else {
                /*新增数据*/
                userMapper.insert(user);
            }
            return Result.success();
        }catch (Exception e){
            return Result.error();
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result UserDelete(@PathVariable Integer id){
        userMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deletes")
    @Transactional(rollbackFor = Exception.class)
    public Result UserDeleteBath(@RequestBody List<Integer> list){
            for(Integer id : list){
                this.UserDelete(id);
            }
            return Result.success();
    }
}
