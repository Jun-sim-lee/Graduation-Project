<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" style = "top: 422px; padding-left: 3%;" placeholder="아이디" v-model="userId"/>
        <input type = "password" class = "wmi_input" style = "top: 477px; padding-left: 3%;" placeholder="비밀번호" v-model="userPw"/>
        <button class = "submit_form" style = "top: 570px" @click="sentToServer">로그인</button>
    </div>
</template>

<script>
export default{
    data(){
        return{
            userId: "",
            userPw: ""
        }
    },
    methods: {
        sentToServer(){
            alert("서버로 " + this.userId + " " + this.userPw + " 를 전송합니다.")
            this.$router.replace('otp')
        }
    }
}
</script>

<style>
@import "../style/wmistyle.css";
</style>