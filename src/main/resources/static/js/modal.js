// モーダル要素とボタン要素の取得
var modal = document.getElementById('createUserModal');
var openModalButton = document.getElementById('openModal');
var closeModalButton = document.querySelector('.close');

// モーダルを開くイベントリスナー
openModalButton.onclick = function() {
    modal.style.display = 'block';
};

// モーダルを閉じるイベントリスナー
closeModalButton.onclick = function() {
    modal.style.display = 'none';
};

// モーダル外をクリックした場合にモーダルを閉じる
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
};
