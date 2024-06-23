# REMS

**Language / 语言: [English](#english) | [中文](#中文)**

---

## English

REMS is a Research Equipment Management System.

- Architecture: B/S
- Frontend: Vue
- Backend: SpringBoot + MySQL

## Access Control

REMS supports three access rights:
- User
- Administrator
- Super Administrator

## Features

- **View and Reserve Equipment**: All users can view and reserve equipment.
- **Manage Equipment Categories**: Administrators and Super Administrators can add, query, delete, and modify categories.
- **Manage Research Equipment**: Administrators and Super Administrators can add, query, delete, and modify equipment.
- **Approve Equipment Reservation Requests**: Administrators can approve or reject equipment reservation requests for their own units; Super Administrators can approve all equipment reservation requests.
- **Approve Administrator Applications**: Registering as an administrator requires an application to be submitted to the Super Administrator. The administrator's privileges take effect only after approval (Super Administrator only).

## How to Deploy and Run the Project Locally

1. Run the backend: Execute `RemsBackendApplication` in your IDE.
2. Run the frontend:
    ```shell
    cd rems_frontend
    npm run dev
    ```

---

## 中文

REMS是一个科研设备管理系统。
- 架构：B/S
- 前端：Vue
- 后端：SpringBoot + MySQL

## 权限管理

REMS支持三种权限管理：
- 普通用户
- 管理员
- 超级管理员

## 系统功能

- **查看设备并申请预约设备**（所有权限）
- **管理设备分类**：添加、查询、删除、修改分类（管理员、超级管理员）
- **管理科研设备**：添加、查询、删除、修改设备（管理员、超级管理员）
- **审核设备预约申请**：管理员能够通过/拒绝对本单位科研设备的预约申请；超级管理员能够审批所有的设备申请。（管理员或超级管理员）
- **审核管理员申请**：注册管理员身份需要向超级管理员申请，申请通过后管理员权限方能生效。（仅超级管理员）

## 如何在本地部署并运行项目

1. 运行后端：在IDEA中运行后端`RemsBackendApplication`
2. 运行前端：
    ```shell
    cd rems_frontend
    npm run dev
    ```