<template>
    <div>
        <div class="mg-10">
            <el-button type="primary" class="ml-10" @click="opens">增加<i class="el-icon-bottom"></i></el-button>
            <el-popconfirm
                    class="ml-10"
                    confirm-button-text='好的'
                    cancel-button-text='我再想想'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定删除吗？"
                    @confirm="deletebatch">
                <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="140" fixed></el-table-column>
            <el-table-column prop="role" label="角色身份" width="320"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
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
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="100"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px"  size="small">
                   <p>角色录入</p><br><br>
                <el-form-item label="id">
                    <el-input v-model="form.id" disabled autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色定位">
                    <el-input v-model="form.role" autocomplete="off"></el-input>
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
        name: "File",
        created() {
             this.getRole();
        },
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                name: '',
                dialogFormVisible: false,
                form: {}
            }
        },
        methods: {
            getRole(){
                this.request.get('/role/get',{params:{
                        pageSize: this.pageSize,
                        pageNum: this.pageNum
                    }}).then(res => {
                    this.tableData=res.data.data;
                    this.total=res.data.total;
                })
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.getRole();
            },
            handleCurrentChange(pageNUm) {
                this.pageNum = pageNUm;
                this.getRole();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            opens(){
                this.dialogFormVisible=true;
            },
            save(){
                this.dialogFormVisible=false;
                this.request.post("/role/save",this.form).then(res =>{
                    if(res.code !== '200'){
                        this.$message.error(res.msg);
                    }else {
                        this.getRole();
                        this.$message.success(res.msg);
                    }
                })
            },
            deletes(id){
                this.request.delete('/role/delete/'+id).then(res => {
                    this.getRole();
                    this.$message.success(res.msg);
                })
            },
            deletebatch(){
                let ids=this.multipleSelection.map( v=> v.id);
                this.request.post('/role/delete',ids).then(res => {
                    this.getRole();
                    this.$message.success(res.msg);
                })
            }
        }
    }
</script>

<style>
    .avatar-uploader {
        text-align: center;
        padding-bottom: 10px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 138px;
        height: 138px;
        line-height: 138px;
        text-align: center;
    }
    .avatar {
        width: 138px;
        height: 138px;
        display: block;
    }
</style>