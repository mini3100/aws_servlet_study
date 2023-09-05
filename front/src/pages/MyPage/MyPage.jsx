import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import axios from 'axios';

function MyPage(props) {
    const [ profile, setProfile ] = useState({
        username: "",
        password: "",
        name: "",
        email: ""
    });

    // 페이지 첫 로드시(마운트) 서버에서 유저 정보 가져오도록
    useEffect(() => {
        const getProfile = async () => {
            try{
                const response = await axios.get(`http://localhost:8080/servlet_study_ga0/mypage/profile`, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                });
                setProfile(response.data);
            } catch(error) {
                console.log(error);
            }
        }
        getProfile();
    }, []);

    return (
        <>
            <h1>마이페이지</h1>
            <p>username: {profile?.username}</p>
            <p>password: {profile?.password}</p>
            <p>name: {profile?.name}</p>
            <p>email: {profile?.email}</p>
        </>
    );
}

export default MyPage;