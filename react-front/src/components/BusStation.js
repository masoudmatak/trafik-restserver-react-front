import React, { useEffect, useState } from "react";
import axios from 'axios'

function Fetcher(){
   const [posts, setPosts] = useState([])
  useEffect(() => {
     axios.get('http://localhost:8080/TrafikREST/rest/stationer')
     .then(res => {
     console.log(res)
     setPosts(res.data)
     })
     .catch(err => {
     console.log(err)
    })
 },[])
 return (
  <div>
     <ul>
	 <table border="1"><tr><th>Buss Number</th><th>Stop Points</th></tr>
      {posts.map (post => (
       
		<tr  key={post.busNumber}><th>buss {post.busNumber}</th><td>{post.Stationer}</td></tr>
      ))}
	   </table>
     </ul>
   </div>
  )
}

export default Fetcher