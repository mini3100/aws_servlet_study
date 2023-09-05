import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SInputLayout = css`
    margin-bottom: 15px;
    width: 60%;
    height: 40px;

    & > * {
        width: 100%;
        height: 100%;
        text-align: center;
    }
`;

function EditProfile(props) {
    const navigate = useNavigate();
    const [ profile, setProfile ] = useState();

    // 마운트 될 때 유저 정보 가져오기
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
    }, [])

    const handleInputChange = (e) => {
        setProfile({
            ...profile,
            [e.target.name]: e.target.value
        });
    }

    const handleUpdateClick = () => {
        const submit = async () => {
            const option = {
                headers: {
                    "Authorization": localStorage.getItem("token")
                }
            }
            const response = await axios.put("http://localhost:8080/servlet_study_ga0/mypage/profile", profile, option);
            if(response.data) {
                alert("수정 완료!");
                navigate("/mypage");
                return;
            }
        }
        submit();
    }

    return (
        <>
            <h1>회원정보 수정</h1>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='username' placeholder='username' defaultValue={profile?.username}/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="password" name='password' placeholder='password' defaultValue={profile?.password}/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='name' placeholder='name' defaultValue={profile?.name}/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='email' placeholder='email' defaultValue={profile?.email}/>
            </div>
            <div>
                <button onClick={handleUpdateClick}>가입하기</button>
            </div>
        </>
    );
}

export default EditProfile;