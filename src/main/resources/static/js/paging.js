$(document).ready(function() {
	$('#paging').DataTable({
		// 検索機能 無効
		searching: false,
		// 日本語表示
		"language": {
			"url": "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
		}
	});
});
