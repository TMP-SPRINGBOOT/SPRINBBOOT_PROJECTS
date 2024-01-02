		const formData = new FormData();	//폼관련 정보 저장



		const uploadBox_el = document.querySelector('.upload-box');
		//dragenter / dragover /dragleave / drop

		uploadBox_el.addEventListener('dragenter',function(e){
			e.preventDefault();
			console.log("dragenter...");
		});
		uploadBox_el.addEventListener('dragover',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='0.5';
			console.log("dragover...");

		});
		uploadBox_el.addEventListener('dragleave',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='1';
			console.log("dragleave...");

		});

        //----------------------------------------------
        //
        //----------------------------------------------
		uploadBox_el.addEventListener('drop',function(e){
			e.preventDefault();
			console.log("drop...");
			console.log(e);
			console.log(e.dataTransfer);
			console.log(e.dataTransfer.files[0]);

            const file = e.dataTransfer.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload=function(e){
                const preview = document.querySelector('#preview');
                const imgEl =  document.createElement('img');
                console.log("reader.onload",e)
                imgEl.setAttribute('src',e.target.result);
                preview.appendChild(imgEl);
            }


            formData.append('files',file);
            console.log("formData",formData);

		});


		const add_product_btn_el = document.querySelector('.add_product_btn');
		add_product_btn_el.addEventListener('click',function(){
            const seller = document.imageform.seller.value;
            const productname = document.imageform.productname.value;
            const category = document.imageform.category.value;
            const brandname = document.imageform.brandname.value;
            const itemdetals = document.imageform.itemdetals.value;
            const amount = document.imageform.amount.value;
            const size = document.imageform.size.value;



		})