<template>
  <input style="width: 74px;" type="file" id="uFile" name="uFile" @change="upload($event)"/>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods:{
    upload(e){
      console.log("1")
      const that = this;
      const files = document.getElementById('uFile').value;
      if (!/\.(gif|jpg|jpeg|png|gif|jpg|png)$/i.test(files)) {
        console.log("图片类型必须是.gif,jpeg,jpg,png中的一种,请重新上传")
        return false;
      }
      let file = e.target.files[0]
      let param = new FormData()       // 创建form对象
      param.append('file', file)       // 通过append向form对象添加数据
      param.append("需要接受的数据名", "数据"); // 添加form表单中其他数据
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      this.axios.post("uploadLogo",param, config).then((res)=>{
        if(res.succeed){
          console.log("添加成功")  //需要引入elemrnt
        }else{
          console.log("添加失败")
        }
      }).catch((err)=>{
        console.log("图片上传失败，请重新上传!")
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
