package com.mayank.webkorps_task.ui.main.add

import android.Manifest
import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.FragmentAddBinding
import com.mayank.webkorps_task.room.LocaleDataBase
import com.mayank.webkorps_task.room.dao.UserDao
import com.mayank.webkorps_task.room.entity.User
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var localeDataBase: LocaleDataBase
    private lateinit var userDao: UserDao

    private var dob: Calendar = Calendar.getInstance()
    private var image : ByteArray? = null

    val PERMISSION_ALL = 100

    val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    companion object {
        private const val TYPE = "type"
        private const val ID = "id"
        fun newInstance(type: String): AddFragment {
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            val fragment = AddFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(type: String, id: Long): AddFragment {
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            bundle.putLong(ID, id)
            val fragment = AddFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        init()

        return binding.root
    }

    private fun init() {
        localeDataBase = LocaleDataBase.getAppDataBase(context!!)
        userDao = localeDataBase.getUserDao()

        var type = arguments?.getString(TYPE)
        if(type.equals("edit")){
            setUserInfo()
        }

        binding.clMain.setOnClickListener {
            if (!hasPermissions(context, *PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity!!, PERMISSIONS, PERMISSION_ALL)
            } else {
                with(this)
                    .crop() //Crop image(Optional), Check Customization for more option
                    .compress(1024) //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)
                    .start()
            }
        }

        binding.btMain.setOnClickListener {
            if(image==null){
                Toast.makeText(context, "Please select image", Toast.LENGTH_LONG).show()
            }
            if (binding.etName.text.isEmpty()) {
                Toast.makeText(context, "Please enter name", Toast.LENGTH_LONG).show()
            } else if (binding.etEmail.text.isEmpty()) {
                Toast.makeText(context, "Please enter email", Toast.LENGTH_LONG).show()
            } else if (binding.etDob.text.isEmpty()) {
                Toast.makeText(context, "Please select date of birth", Toast.LENGTH_LONG).show()
            } else {
                save()
            }
        }

        binding.etDob.setOnClickListener {

            val day = dob[Calendar.DAY_OF_MONTH]
            val month = dob[Calendar.MONTH]
            val year = dob[Calendar.YEAR]


            var datepicker: DatePickerDialog = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->

                    dob = Calendar.getInstance()
                    dob.set(Calendar.YEAR, year)
                    dob.set(Calendar.MONTH, monthOfYear)
                    dob.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    binding.etDob.setText(SimpleDateFormat("dd-MM-yyyy").format(dob.getTime()))

                }, year, month, day
            )
            datepicker!!.maxDate = Calendar.getInstance()
            datepicker!!.show(childFragmentManager, "apt")

        }
    }

    private fun save() {
        var type = arguments?.getString(TYPE)
        if(type.equals("edit")){
            var id = arguments?.getLong(ID)
            userDao.insertUser(
                User(
                    id = id!!,
                    name = binding.etName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    dob = dob,
                    image = image!!
                )
            )
            Toast.makeText(context, "User update...", Toast.LENGTH_LONG).show()
        }
        else{
            userDao.insertUser(
                User(
                    name = binding.etName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    dob = dob,
                    image = image!!
                )
            )
            Toast.makeText(context, "User Save...", Toast.LENGTH_LONG).show()
        }
        emptyAll()
    }

    private fun setUserInfo() {
        var id = arguments?.getLong(ID)
        var user = userDao.getUser(id!!)

        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etDob.setText(SimpleDateFormat("dd-MM-yyyy").format(user.dob.getTime()))
        binding.ivMain.setImageBitmap(BitmapFactory.decodeByteArray(user.image, 0, user.image.size))
        dob = user.dob
        image = user.image
    }

    fun emptyAll() {
        binding.etDob.setText("")
        binding.etName.setText("")
        binding.etEmail.setText("")
    }

    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ALL -> {
                if (grantResults.size > 0) {
                    var b = true
                    for (per in grantResults) {
                        if (per == PackageManager.PERMISSION_DENIED) {
                            b = false
                        }
                    }
                    if (b) {
                        with(this)
                            .crop() //Crop image(Optional), Check Customization for more option
                            .compress(1024) //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)
                            .start()
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            val iStream: InputStream = context?.contentResolver?.openInputStream(uri!!)!!
            image = getBytes(iStream)
            binding.ivMain.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image?.size!!))
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }


}