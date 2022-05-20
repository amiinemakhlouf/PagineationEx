package esprims.gi2.firebasepagination.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import esprims.gi2.firebasepagination.MainActivity
import esprims.gi2.firebasepagination.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val first = Firebase.firestore.collection("matiere")
            .limit(2)

        first.get()
            .addOnSuccessListener { documentSnapshots ->

                for (
                document in documentSnapshots.documents
                ){
                    val matiere=document.toObject<MainActivity.Matiere>()
                    Log.d("testit",matiere?.nom!!)
                }

                val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]
                // Construct a new query starting at this document,
                // get the next 25 cities.
                var next = Firebase.firestore.collection("matiere")
                    .startAfter(lastVisible)
                    .limit(2)
                next.get().addOnSuccessListener {
                        for (
                        document in it.documents
                        ){
                            val matiere=document.toObject<MainActivity.Matiere>()
                            Log.d("testit",matiere?.nom!!)

                        }
                    }



            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}