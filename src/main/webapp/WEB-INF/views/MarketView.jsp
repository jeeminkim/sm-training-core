<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>SM Training - Market Watch</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 24px;
            color: #222;
        }

        h1 {
            margin-bottom: 8px;
        }

        .description {
            margin-bottom: 20px;
            color: #555;
        }

        .box {
            border: 1px solid #ddd;
            padding: 14px;
            margin-bottom: 16px;
            border-radius: 6px;
        }

        label {
            display: inline-block;
            width: 90px;
            margin-bottom: 8px;
        }

        input {
            padding: 6px;
            margin-bottom: 8px;
            width: 220px;
        }

        button {
            padding: 7px 12px;
            margin: 4px;
            cursor: pointer;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 12px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 9px;
            text-align: left;
        }

        th {
            background: #f5f5f5;
        }

        .log {
            background: #111;
            color: #00e676;
            padding: 12px;
            min-height: 120px;
            white-space: pre-wrap;
            font-family: Consolas, monospace;
            font-size: 13px;
        }

        .flow {
            background: #f7f7f7;
            padding: 12px;
            white-space: pre-wrap;
            font-family: Consolas, monospace;
            font-size: 13px;
        }
    </style>
</head>

<body onload="onLoad()">

<h1>SM Training - Market Watch</h1>

<div class="description">
    JSP + Vanilla JS Operation → Spring Controller → Process Service → Interface → Implementation → DTO 흐름을 학습하는 화면입니다.
</div>

<div class="box">
    <h3>구조 흐름</h3>
    <div class="flow">MarketView.jsp
  → JavaScript Operation
  → MarketController
  → WatchItemProcessService
  → WatchItemStore Interface
  → InMemoryWatchItemStore Implementation
  → WatchItemDto</div>
</div>

<div class="box">
    <h3>조회 조건</h3>

    <label>마켓명</label>
    <input type="text" id="searchMarketName" placeholder="예: 비트코인">
    <button type="button" onclick="selectList()">목록 조회</button>
    <button type="button" onclick="deleteAll()">전체 삭제</button>
</div>

<div class="box">
    <h3>관심 항목 등록</h3>

    <label>마켓코드</label>
    <input type="text" id="marketCode" placeholder="예: KRW-BTC"><br>

    <label>마켓명</label>
    <input type="text" id="marketName" placeholder="예: 비트코인"><br>

    <label>사용여부</label>
    <input type="text" id="useYn" value="Y"><br>

    <button type="button" onclick="insertOne()">단건 등록</button>
</div>

<div class="box">
    <h3>목록</h3>

    <table>
        <thead>
        <tr>
            <th>마켓코드</th>
            <th>마켓명</th>
            <th>사용여부</th>
            <th>상세</th>
        </tr>
        </thead>
        <tbody id="watchTableBody">
        </tbody>
    </table>
</div>

<div class="box">
    <h3>상세</h3>

    <div id="detailArea">
        상세 조회 결과가 없습니다.
    </div>
</div>

<div class="box">
    <h3>Operation Log</h3>
    <div class="log" id="logArea"></div>
</div>

<script>
    /**
     * Operation: onLoad
     *
     * 화면이 최초 로딩될 때 자동 실행된다.
     * 회사 JSP 설계서의 onLoad 오퍼레이션과 같은 역할이다.
     */
    function onLoad() {
        writeLog("onLoad 실행");
        selectList();
    }

    /**
     * Operation: selectList
     *
     * 목록 조회 버튼 클릭 또는 onLoad에서 호출된다.
     *
     * 흐름:
     * selectList()
     * → GET /market/selectList.do
     * → MarketController.selectList()
     * → WatchItemProcessService.selectList()
     */
    function selectList() {
        var marketName = document.getElementById("searchMarketName").value;

        var url = getContextPath() + "/market/selectList.do?marketName=" + encodeURIComponent(marketName);

        writeLog("selectList 요청: " + url);

        fetch(url, {
            method: "GET"
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (result) {
                writeLog("selectList 응답: " + JSON.stringify(result, null, 2));

                if (!result.success) {
                    alert(result.message || "목록 조회 실패");
                    return;
                }

                renderList(result.data);
            })
            .catch(function (error) {
                writeLog("selectList 오류: " + error.message);
                alert("목록 조회 중 오류가 발생했습니다.");
            });
    }

    /**
     * Operation: insertOne
     *
     * 입력 폼 값을 서버로 전송해 단건 등록한다.
     *
     * 흐름:
     * insertOne()
     * → POST /market/insertOne.do
     * → MarketController.insertOne()
     * → WatchItemProcessService.insertOne()
     * → validation
     * → InMemoryWatchItemStore.insertOne()
     */
    function insertOne() {
        var formData = new URLSearchParams();

        formData.append("marketCode", document.getElementById("marketCode").value);
        formData.append("marketName", document.getElementById("marketName").value);
        formData.append("useYn", document.getElementById("useYn").value);

        var url = getContextPath() + "/market/insertOne.do";

        writeLog("insertOne 요청: " + formData.toString());

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body: formData.toString()
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (result) {
                writeLog("insertOne 응답: " + JSON.stringify(result, null, 2));

                if (!result.success) {
                    alert(result.message || "등록 실패");
                    return;
                }

                alert(result.message);
                selectList();
            })
            .catch(function (error) {
                writeLog("insertOne 오류: " + error.message);
                alert("등록 중 오류가 발생했습니다.");
            });
    }

    /**
     * Operation: selectDetail
     *
     * 목록의 상세 버튼을 눌렀을 때 실행된다.
     *
     * 흐름:
     * selectDetail()
     * → GET /market/selectDetail.do
     * → MarketController.selectDetail()
     * → WatchItemProcessService.selectDetail()
     */
    function selectDetail(marketCode) {
        var url = getContextPath() + "/market/selectDetail.do?marketCode=" + encodeURIComponent(marketCode);

        writeLog("selectDetail 요청: " + url);

        fetch(url, {
            method: "GET"
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (result) {
                writeLog("selectDetail 응답: " + JSON.stringify(result, null, 2));

                if (!result.success) {
                    alert(result.message || "상세 조회 실패");
                    return;
                }

                renderDetail(result.data);
            })
            .catch(function (error) {
                writeLog("selectDetail 오류: " + error.message);
                alert("상세 조회 중 오류가 발생했습니다.");
            });
    }

    /**
     * Operation: deleteAll
     *
     * 전체 삭제 버튼을 눌렀을 때 실행된다.
     *
     * 흐름:
     * deleteAll()
     * → POST /market/deleteAll.do
     * → MarketController.deleteAll()
     * → WatchItemProcessService.deleteAll()
     */
    function deleteAll() {
        if (!confirm("전체 관심 항목을 삭제하시겠습니까?")) {
            return;
        }

        var url = getContextPath() + "/market/deleteAll.do";

        writeLog("deleteAll 요청");

        fetch(url, {
            method: "POST"
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (result) {
                writeLog("deleteAll 응답: " + JSON.stringify(result, null, 2));

                if (!result.success) {
                    alert(result.message || "삭제 실패");
                    return;
                }

                alert(result.message);
                selectList();
            })
            .catch(function (error) {
                writeLog("deleteAll 오류: " + error.message);
                alert("삭제 중 오류가 발생했습니다.");
            });
    }

    function renderList(list) {
        var tbody = document.getElementById("watchTableBody");
        tbody.innerHTML = "";

        if (!list || list.length === 0) {
            var emptyRow = document.createElement("tr");
            emptyRow.innerHTML = "<td colspan='4'>조회 결과가 없습니다.</td>";
            tbody.appendChild(emptyRow);
            return;
        }

        for (var i = 0; i < list.length; i++) {
            var item = list[i];

            var tr = document.createElement("tr");

            tr.innerHTML =
                "<td>" + escapeHtml(item.marketCode) + "</td>" +
                "<td>" + escapeHtml(item.marketName) + "</td>" +
                "<td>" + escapeHtml(item.useYn) + "</td>" +
                "<td><button type='button' onclick=\"selectDetail('" + escapeJs(item.marketCode) + "')\">상세</button></td>";

            tbody.appendChild(tr);
        }
    }

    function renderDetail(item) {
        var detailArea = document.getElementById("detailArea");

        if (!item) {
            detailArea.innerHTML = "상세 조회 결과가 없습니다.";
            return;
        }

        detailArea.innerHTML =
            "<strong>마켓코드:</strong> " + escapeHtml(item.marketCode) + "<br>" +
            "<strong>마켓명:</strong> " + escapeHtml(item.marketName) + "<br>" +
            "<strong>사용여부:</strong> " + escapeHtml(item.useYn);
    }

    function writeLog(message) {
        var logArea = document.getElementById("logArea");
        var now = new Date().toLocaleString();

        logArea.textContent = "[" + now + "] " + message + "\n\n" + logArea.textContent;
    }

    function getContextPath() {
        return "<%= request.getContextPath() %>";
    }

    function escapeHtml(value) {
        if (value === null || value === undefined) {
            return "";
        }

        return String(value)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

    function escapeJs(value) {
        if (value === null || value === undefined) {
            return "";
        }

        return String(value)
            .replace(/\\/g, "\\\\")
            .replace(/'/g, "\\'");
    }
</script>

</body>
</html>