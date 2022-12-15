<h1 align="center">CLEVERTEC</h1>
<h2 align="center">🚀 Test task - Receipt API</h2>

[![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://www.repostatus.org/badges/latest/wip.svg)](https://www.repostatus.org/#wip)
[![GitHub](https://img.shields.io/github/license/IvanHayel/clevertec-test-task)](https://github.com/IvanHayel/clevertec-test-task/blob/master/LICENSE.md)
[![HitCount](http://hits.dwyl.com/IvanHayel/clevertec-test-task.svg?style=flat&show=unique)](http://hits.dwyl.com/IvanHayel/clevertec-test-task)
[![GitHub](https://img.shields.io/github/followers/IvanHayel?label=Follow&style=social)](https://github.com/IvanHayel)

<details>
  <summary style="font-weight: bold; font-size: large">Table of Contents</summary>
  <ol>
    <li>
      <a href="#-task-description">Task Description</a>
      <ul>
        <li><a href="#-task">Task</a></li>
        <li><a href="#-main-technologies">Main Technologies</a></li>
      </ul>
    </li>
    <li>
      <a href="#%EF%B8%8F-getting-started">Getting Started</a>
      <ul>
        <li><a href="#-the-easiest-way-">The Easiest Way</a></li>
        <li><a href="#-start-with-docker">Start with Docker</a></li>
        <li><a href="#-local-startup-with-gradle">Local Startup with Gradle</a></li>
      </ul>
    </li>
    <li>
      <a href="#-api-usage">API Usage</a>
      <ul>
        <a href="#-endpoints">Endpoints</a>
        <ul>
            <li><a href="#-products">Products</a></li>
            <li><a href="#-discount-cards">Discount Cards</a></li>
            <li><a href="#-receipts">Receipts</a></li>
        </ul>
      </ul>
    </li>
    <li>
      <a href="#-demo">Demo</a>
      <ul>
        <li>
            <a href="#-products-demo">Products Demo</a>
            <ul>
                <li><a href="#get-all-products">Get all products</a></li>
                <li><a href="#get-product-by-id">Get product by id</a></li>
                <li><a href="#products-full-text-search">Products full text search</a></li>
                <li><a href="#add-new-product">Add new product</a></li>
                <li><a href="#update-existing-product">Update existing product</a></li>
                <li><a href="#delete-product">Delete product</a></li>
            </ul>
        </li>
        <li>
            <a href="#-discount-cards-demo">Discount Cards Demo</a>
            <ul>
                <li><a href="#get-all-cards">Get all cards</a></li>
                <li><a href="#get-card-by-id">Get card by id</a></li>
                <li><a href="#add-new-card">Add new card</a></li>
                <li><a href="#update-existing-card">Update existing card</a></li>
                <li><a href="#delete-card">Delete card</a></li>
            </ul>
        </li>
        <li>
            <a href="#-receipts-demo">Receipts Demo</a>
            <ul>
                <li><a href="#get-all-receipts">Get all receipts</a></li>
                <li><a href="#get-receipt-by-id">Get receipt by id</a></li>
                <li><a href="#receipts-full-text-search">Receipts full text search</a></li>
                <li><a href="#download-receipt-in-pdf">Download receipt in PDF</a></li>
                <li><a href="#add-new-receipt">Add new receipt</a></li>
                <li><a href="#update-existing-receipt">Update existing receipt</a></li>
                <li><a href="#delete-receipt">Delete receipt</a></li>
            </ul>
        </li>
      </ul>
    </li>
  </ol>
</details>

## 📄 Task Description

### [📝 Task](./documentation/task.pdf)

### 📝 Main Technologies

|  **Database**  |                                                              [![Postgresql](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)                                                              |
|:--------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|  **Backend**   |         [![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://dev.java/) [![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)          |
| **Build Tool** |                                                                        [![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)](https://gradle.org/)                                                                         |
|    **PaaS**    | [![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/) [![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)](https://www.heroku.com/) |

---

## ⚙️ Getting Started

### ⏩ The Easiest Way ⏩

<strong>API already deployed on PaaS Heroku:</strong>

### 🚀 Heroku link

```
https://clevertec-test-task.herokuapp.com
```

#### <strong><a href="#-api-usage">JUMP TO API USAGE</a></strong>

### 🐋 Start with Docker

* Clone the repository

```console
git clone https://github.com/IvanHayel/clevertec-test-task.git
```

* Use docker-compose

```console
docker-compose up
```

#### <strong><a href="#-api-usage">JUMP TO API USAGE</a></strong>

### 🦖 Local Startup with Gradle

> Java version 17+ is required.

* Clone the repository

```console
git clone https://github.com/IvanHayel/clevertec-test-task.git
```

* Create Postgres Database

Example:

```sql
CREATE DATABASE "clevertec-api"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_World.1252'
    LC_CTYPE = 'English_World.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

* Set up environment variables

> DB_URL - JDBC url for Postgres database.
>
> DB_USERNAME - database username
>
> DB_PASSWORD - database password

* Run Gradle

```console
gradle clean build bootRun
```

> You can also use the gradle wrapper: `./gradlew`

---

## 🔥 API Usage

### 💠 Endpoints

<h3 align="center">📦 PRODUCTS</h2>

| **HTTP METHOD** |          **URL**          |      **QUERY PARAMETERS**       |                            **TEMPLATE**                             | *DESCRIPTION*                        |
|:---------------:|:-------------------------:|:-------------------------------:|:-------------------------------------------------------------------:|--------------------------------------|
|     **GET**     |    `/api/v1/products`     |              none               |                                none                                 | Getting all `Products`.              |
|     **GET**     |  `/api/v1/products/{id}`  |              none               |                                none                                 | Getting `Product` by id.             |
|     **GET**     | `/api/v1/products/search` | `term`: {any} - <i>required</i> |                                none                                 | Full text search for all `Products`. |
|    **POST**     |    `/api/v1/products`     |              none               | ![post-template](./documentation/images/template/products-post.png) | Creating a new `Product`.            |
|     **PUT**     |    `/api/v1/products`     |              none               |  ![put-template](./documentation/images/template/products-put.png)  | Updating existing `Product`.         |
|   **DELETE**    |  `/api/v1/products/{id}`  |              none               |                                none                                 | Deleting `Product` by id.            |

<h3 align="center">💳 DISCOUNT CARDS</h2>

| **HTTP METHOD** |       **URL**        | **QUERY PARAMETERS** |                           **TEMPLATE**                           | *DESCRIPTION*                      |
|:---------------:|:--------------------:|:--------------------:|:----------------------------------------------------------------:|------------------------------------|
|     **GET**     |   `/api/v1/cards`    |         none         |                               none                               | Getting all `Discount Cards`.      |
|     **GET**     | `/api/v1/cards/{id}` |         none         |                               none                               | Getting `Discount Card` by id.     |
|    **POST**     |   `/api/v1/cards`    |         none         | ![post-template](./documentation/images/template/cards-post.png) | Creating a new `Discount Card`.    |
|     **PUT**     |   `/api/v1/cards`    |         none         |  ![put-template](./documentation/images/template/cards-put.png)  | Updating existing `Discount Card`. |
|   **DELETE**    | `/api/v1/cards/{id}` |         none         |                               none                               | Deleting `Discount Card` by id.    |

<h3 align="center">🧾 RECEIPTS</h2>

| **HTTP METHOD** |             **URL**              |      **QUERY PARAMETERS**       |                            **TEMPLATE**                             | *DESCRIPTION*                              |
|:---------------:|:--------------------------------:|:-------------------------------:|:-------------------------------------------------------------------:|--------------------------------------------|
|     **GET**     |        `/api/v1/receipts`        |              none               |                                none                                 | Getting all `Receipts`.                    |
|     **GET**     |     `/api/v1/receipts/{id}`      |              none               |                                none                                 | Getting `Receipt` by id.                   |
|     **GET**     |    `/api/v1/receipts/search`     | `term`: {any} - <i>required</i> |                                none                                 | Full text search for all `Receipts`.       |
|     **GET**     | `/api/v1/receipts/download/{id}` |              none               |                                none                                 | Download existing `Receipt` in PDF format. |
|    **POST**     |        `/api/v1/receipts`        |              none               | ![post-template](./documentation/images/template/receipts-post.png) | Creating a new `Receipt`.                  |
|     **PUT**     |        `/api/v1/receipts`        |              none               |  ![put-template](./documentation/images/template/receipts-put.png)  | Updating existing `Receipt`.               |
|   **DELETE**    |     `/api/v1/receipts/{id}`      |              none               |                                none                                 | Deleting `Receipt` by id.                  |

---

## 🪄 Demo

> Insomnia used for demonstration.

<h3 align="center">📦 PRODUCTS DEMO</h2>

### Get all products

![products-all](./documentation/images/demo/products-get-all.png)

### Get product by id

![products-by-id](./documentation/images/demo/products-get-by-id.png)

### Products full text search

![products-search](./documentation/images/demo/products-search.png)

### Add new product

![products-add](./documentation/images/demo/products-add.png)

### Update existing product

![products-update](./documentation/images/demo/products-update.png)

### Delete product

![products-delete](./documentation/images/demo/products-delete.png)

<h3 align="center">💳 DISCOUNT CARDS DEMO</h2>

### Get all cards

![cards-all](./documentation/images/demo/cards-get-all.png)

### Get card by id

![cards-by-id](./documentation/images/demo/cards-get-by-id.png)

### Add new card

![cards-add](./documentation/images/demo/cards-add.png)

### Update existing card

![cards-update](./documentation/images/demo/cards-update.png)

### Delete card

![cards-delete](./documentation/images/demo/cards-delete.png)

<h3 align="center">🧾 RECEIPTS DEMO</h2>

### Get all receipts

![receipts-all](./documentation/images/demo/receipts-get-all.png)

### Get receipt by id

![receipts-by-id](./documentation/images/demo/receipts-get-by-id.png)

### Receipts full text search

![receipts-search](./documentation/images/demo/receipts-search.png)

### Download receipt in PDF
![receipts-download-pdf](./documentation/images/demo/receipts-download-pdf.png)

### Add new receipt

![receipts-add](./documentation/images/demo/receipts-add.png)

### Update existing receipt

![receipts-update](./documentation/images/demo/receipts-update.png)

### Delete receipt

![receipts-delete](./documentation/images/demo/receipts-delete.png)