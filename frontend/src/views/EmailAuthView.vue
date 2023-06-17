<template>        
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" style="top: 377px; padding-left: 3%;" placeholder="부산대학교 이메일"/>
        <button class = "submit_form" style="top: 488px">이메일 인증하기</button>
        <router-link to = "/main" class = "submit_form" style="top: 538px">인증없이 진행하기</router-link>
        <p style = "position: absolute; top: 588px; width: 250px; font-size: 15px; color: #808080;">※ 주의사항!<br>
            <br>
            학교 이메일 인증 없이 진행하실 경우<br>
            현재 내 위치 확인만 가능합니다.<br>
            <br>
            추후 이메일 인증을 진행하실 수 있습니다.
        </p>
    </div>
</template>

<style>
@import "../style/wmistyle.css";
</style>