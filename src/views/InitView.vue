<!-- 초기 화면 페이지 -->
<template>
   <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <router-link to="/login" class = "wmi_input" style = "text-align: center; text-decoration: none; 
                                                                  color: black; line-height: 40px; top: 422px">
                로그인</router-link>
            <router-link to="/signup" class = "wmi_input" style = "text-align: center; text-decoration: none; 
                                                                  color: black; line-height: 40px; top: 477px">
                회원가입</router-link>
        </div>
    </div>
</template>

<script>
export default {}
</script>

<style>
@import "../style/wmistyle.css";
</style>