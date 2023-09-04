import React, { useState } from 'react';
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

function Signup(props) {
    const navigate = useNavigate();

    const [ signupUser, setSignupUser ] = useState({
        username: "",
        password: "",
        name: "",
        email: ""
    });

    const handleInputChange = (e) => {
        setSignupUser({
            ...signupUser,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmitClick = () => {
        // 회원가입 요청
        const option = {
            params: {
                username: signupUser.username
            }
        };

        const signup = async () => {
            let response = await axios.get("http://localhost:8080/servlet_study_ga0/auth/signup/duplicate/username", option);

            if(response.data) { // await을 쓰지 않으면 해당 if문이 먼저 실행되어 빈 값을 가져올 수도 있다.
                alert("중복된 아이디입니다.");
                return;
            }

            try {
                response = await axios.post("http://localhost:8080/servlet_study_ga0/auth/signup", signupUser);
                if(!response.data) {
                    throw new Error(response);
                }
                alert("회원가입 성공!");
                navigate("/signin");
            } catch(error) {
                console.log("회원가입 실패");
            }
        }

        signup();
    }

    return (
        <>
            <h1>회원가입</h1>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='username' placeholder='username'/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="password" name='password' placeholder='password'/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='name' placeholder='name'/>
            </div>
            <div css={SInputLayout}>
                <input onChange={handleInputChange} type="text" name='email' placeholder='email'/>
            </div>
            <div>
                <button onClick={handleSubmitClick}>가입하기</button>
            </div>
        </>
    );
}

export default Signup;