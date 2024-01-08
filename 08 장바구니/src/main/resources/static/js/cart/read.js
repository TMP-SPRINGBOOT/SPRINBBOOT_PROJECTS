//x 버튼 클릭시 장바구니  items 삭제
const cart_delete_btn_els = document.querySelectorAll('.cart_delete_btn');

cart_delete_btn_els.forEach(el=>{

    el.addEventListener('click',function(){
        const cart_id =  this.getAttribute('data-id');
         console.log('cart_id',cart_id);
         axios.delete(`/cart/delete?cart_id=${cart_id}`)
         .then(
            resp=>{
                console.log(resp)
                alert("장바구니에서 항목을 삭제 했습니다.")
                location.reload();
            }
         )
         .catch(err=>{console.log(err)})
    })
})

