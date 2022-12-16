<template>
    <div>

        <div class="mg-10">
            <el-input placeholder="请输入名称(支持模糊查询)" suffix-icon="el-icon-search" style="width: 200px;" v-model="username"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning"  @click="reset">重置</el-button>
        </div>

        <div class="mg-10">
            <el-button type="primary" @click="open">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" @click="deletebatch">批量删除<i class="el-icon-remove-outline"></i></el-button>
            <el-upload action="http://localhost:8080/user/import" style="display: inline-block" class="ml-10" :show-file-list="false"
            accept="xlsx" :on-success="importSuccess" :on-error="importError">
            <el-button type="primary">导入<i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="sent" class="ml-10">导出<i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="createTime" label="创建日期" width="540" fixed>
            </el-table-column>
            <el-table-column prop="username" label="姓名">
            </el-table-column>
            <el-table-column prop="id" label="序列号">
            </el-table-column>
            <el-table-column prop="password" label="密码">
            </el-table-column>
            <el-table-column label="头像" width="140px" align="center">
                  <template slot-scope="slot">
                      <img :src="slot.row.avater" style="width: 50px;height: 50px">
                  </template>
            </el-table-column>
            <el-table-column label="权限管理" prop="role" align="center">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="warning" @click="edit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            class="ml-10"
                            confirm-button-text='好的'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="deletes(scope.row.id)">
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    background
                    :current-page="pageNum"
                    :page-sizes="[10, 2, 5, 20]"
                    :page-size="100"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px"  size="small">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="序列号">
                    <el-input v-model="form.id" disabled autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建日期">
                    <el-input v-model="form.createTime" disabled autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限管理">
                        <el-select v-model="form.role" placeholder="请选择">
                            <el-option
                                    v-for="item in form.role"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "User",
        data(){
            return{
                tableData: [],
                username: '',
                pssaord: '',
                form: {},
                dialogFormVisible: false,
                id: 0,
                pageNum: 1,
                pageSize: 10,
                multipleSelection: [],
                total: 0,
                headerBg: 'headerBg',
                createTime: ''
            }
        },
        created() {
          this.load();
        },
        methods: {
            load() {
                /*请求获取数据*/
                this.request.get("/user/select",{params:{
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        username: this.username}})
                    .then(res => {
                        this.tableData = res.data.data
                        this.total = res.data.total
                    });
            },
            edit(row){
                this.dialogFormVisible=true;
                this.form=row;
                this.request.get('/role/toget').then(res => {
                      this.form.role=res.data;
                })
            },
            save(){
                this.request.post('/user/save',this.form).then(res =>{
                     if(res.code !== '200'){
                         this.$message.error(res.msg);
                         this.dialogFormVisible=false;
                     }else {
                         this.$message.success(res.data);
                         this.dialogFormVisible=false;
                     }
                    this.load();
                })
            },
            open(){
                this.dialogFormVisible=true;
                this.form={};
            },
            deletes(id){
                this.request.delete('/user/delete/'+id).then(res => {
                    this.$message.success(res.msg);
                    this.load();
                })
            },
            reset(){
                this.username='';
                this.load();
            },
            deletebatch(){
                let ids= this.multipleSelection.map( v=> v.id);
                this.request.post('/user/deletes',ids).then(res => {
                    if(res.code !== '200'){
                        this.$message.error("删除失败");
                    }else {
                        this.load();
                        this.$message.success(res.msg);
                    }
                })
            },
            handleSelectionChange(val){
                this.multipleSelection=val;
            },
            sent(){
                window.open('http://localhost:8080/user/export').then(res => {
                    if(res.code !== '200'){
                        this.$message.error(res.msg);
                    }else {
                        this.$message.success(res.msg);
                    }
                })
            },
            importSuccess(){
                this.load();
                this.$message.success("导入成功!");
            },
            handleSizeChange(pageSize){
                this.pageSize=pageSize;
                this.load();
             },
           handleCurrentChange(pageNUm){
                this.pageNum=pageNUm;
                this.load();
    }
        }
    }
</script>

<style scoped>

</style>

<style>
    .headerBg{
        background: #eee!important;
    }
</style>