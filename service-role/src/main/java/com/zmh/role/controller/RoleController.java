package com.zmh.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.zmh.role.entry.Role;
import com.zmh.role.mapper.RoleMapper;
import com.zmh.role.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("/toget")
    public Result RoleGet(){
        List<Role> roles = roleMapper.selectList(new QueryWrapper<>());
        List<String> role = roles.stream().map(Role::getRole).collect(Collectors.toList());
        return Result.success(role);
    }

    @GetMapping("/get")
    public Result RoleGets(@RequestParam Integer pageSize,@RequestParam Integer pageNum){
        try{
            PageHelper.startPage(pageNum,pageSize);
            HashMap map=new HashMap();
            List<Role> roles = roleMapper.selectList(new QueryWrapper<>());
            map.put("data",roles);
            Integer total = roleMapper.selectTotal();
            map.put("total",total);
            return Result.success(map);
        }catch (Exception e){
            return Result.error();
        }
    }

    @PostMapping("/save")
    public Result RoleSave(@RequestBody Role role){
        /*role并不需要需改数据*/
        try{
            roleMapper.insert(role);
            return Result.success();
        }catch (Exception e){
            return Result.error();
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result RoleDelete(@PathVariable Integer id){
        roleMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/delete")
    @Transactional(rollbackFor = Exception.class)
    public Result RoleDeleteBath(@RequestBody List<Integer> list){
        for(Integer id : list){
            this.RoleDelete(id);
        }
        return Result.success();
    }
}
