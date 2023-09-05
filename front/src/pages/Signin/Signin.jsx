import axios from 'axios';
import React, { useState } from 'react';

function Signin(props) {
    const [ signinInput, setSigninInput ] = useState({
        username: "",
        password: ""
    })

    const handleInputChange = (e) => {
        setSigninInput({
            ...signinInput,
            [e.target.name]: e.target.value
        })
    }

    const handleSigninClick = async () => {
        try {
            const response = await axios.post("http://localhost:8080/servlet_study_ga0/auth/signin", signinInput);

            if(!response.data.token) {
                alert("로그인 실패");
                return;
            }

            localStorage.setItem("token", response.data?.token);    // response가 비어있을 수도 있기 때문에 ? 붙여줌
            alert("환영합니다.");
        } catch(error) {
            console.log(error);
        }
    }

    return (
        <>
            <h1>로그인</h1>
            <div><input onChange={handleInputChange} type="text" name='username' placeholder='username'/></div>
            <div><input onChange={handleInputChange} type="password" name='password' placeholder='password'/></div>
            <div><button onClick={handleSigninClick}>로그인</button></div>
        </>
    );
}

export default Signin;