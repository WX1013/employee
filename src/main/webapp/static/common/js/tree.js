function bindTree() {
  $('.left-tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '收起');
  $('.left-tree li.parent_li > span').on('click', function (e) {
    var children = $(this).parent('li.parent_li').find(' > ul > li');
    if (children.is(":visible")) {
      children.hide('fast');
      $(this).attr('title', '展开')
//      $(this).attr('title', 'Expand this branch').find(' > i').addClass('fa-plus').removeClass('fa-minus');
    } else {
      children.show('fast');
      $(this).attr('title', '收起')
//      $(this).attr('title', 'Collapse this branch').find(' > i').addClass('fa-minus').removeClass('fa-plus');
    }
    e.stopPropagation();
  });
}