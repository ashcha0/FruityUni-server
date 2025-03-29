@echo off
echo 开始API测试...

REM 设置基础URL
set BASE_URL=http://localhost:8080

REM 测试注册接口
echo 测试注册接口...
curl -X POST %BASE_URL%/api/auth/register -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"password123\",\"confirmPassword\":\"password123\",\"phone\":\"13800138000\"}"
echo.

REM 测试登录接口
echo 测试登录接口...
curl -X POST %BASE_URL%/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"password123\"}" > token.json
echo.

REM 提取token
for /f "tokens=2 delims=:," %%a in ('type token.json ^| findstr "token"') do (
    set TOKEN=%%a
    set TOKEN=!TOKEN:"=!
    set TOKEN=!TOKEN: =!
)

REM 测试获取用户信息接口
echo 测试获取用户信息接口...
curl -X GET %BASE_URL%/api/users/info -H "Authorization: Bearer %TOKEN%"
echo.

echo API测试完成