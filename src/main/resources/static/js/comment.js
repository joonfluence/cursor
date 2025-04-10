function createComment(postId, parentId = null) {
    const contentId = parentId ? `replyContent-${parentId}` : 'commentContent';
    const content = document.getElementById(contentId).value;
    if (!content.trim()) {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    fetch(`/api/comments/posts/${postId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            content: content,
            postId: postId,
            parentId: parentId
        })
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('댓글 작성에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('댓글 작성 중 오류가 발생했습니다.');
    });
}

function updateComment(id) {
    const content = document.getElementById(`commentContent-${id}`).value;
    if (!content.trim()) {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    fetch(`/api/comments/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            content: content
        })
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('댓글 수정에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('댓글 수정 중 오류가 발생했습니다.');
    });
}

function deleteComment(id) {
    if (!confirm('정말 삭제하시겠습니까?')) {
        return;
    }

    fetch(`/api/comments/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('댓글 삭제에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('댓글 삭제 중 오류가 발생했습니다.');
    });
}

function showReplyForm(parentId) {
    const form = document.getElementById(`replyForm-${parentId}`);
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}